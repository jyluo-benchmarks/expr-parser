package expr_parser.parser.test;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import expr_parser.utils.DirectedGraph;
import expr_parser.utils.TopologicalSort;
import expr_parser.visitors.AntlrExpressionParser;
import expr_parser.visitors.ContextEval;
import expr_parser.visitors.ListVariablesInExpr;

public class ContextEvalVisitorTest
{

	@Test
	public void testContextEval()
	{

		Map<String, Double> context = new HashMap<String, Double>();
		context.put("x", 0.5);
		String expr = "x*(sin(x)^2 + cos(x)^2)";

		assertEquals(evaluateInContext(expr, context), 0.5, 1e-10);
	}

	@Test
	public void testDependencyResolution()
	{

		// make it a linkedHM to enforce unordered dependencies
		Map<String, String> expressions = new LinkedHashMap<String, String>();
		expressions.put("foo", "(sin(x)^2 + cos(x)^2)");
		expressions.put("bar", "(sin(y)^2 + cos(y)^2)");
		expressions.put("x", "0.0");
		expressions.put("IDependOnOthers", "foo/bar + 1");
		expressions.put("y", "3.14");
		expressions.put("w", "0.0");

		// Build expression dependency graph
		DirectedGraph<String> dependencies = new DirectedGraph<String>();
		dependencies.addNodes(expressions.keySet());
		for(Entry<String, String> entry : expressions.entrySet())
		{
			for(String dep : findDeps(entry.getValue()))
			{
				dependencies.addEdge(entry.getKey(), dep);
			}
		}

		// topological sort over dependencies
		List<String> sorted = TopologicalSort.sort(dependencies);
		Collections.reverse(sorted);

		// evaluates all expressions according to dependencies
		Map<String, Double> context = new HashMap<String, Double>();
		for(String exp : sorted)
		{
			Double res = evaluateInContext(expressions.get(exp), context);
			context.put(exp, res);
		}

		assertEquals(context.get("IDependOnOthers"), 2.0, 1e-10);
	}

	private Double evaluateInContext(String expression, Map<String, Double> context)
	{
		AntlrExpressionParser p = new AntlrExpressionParser(expression);
		ContextEval eval = new ContextEval(context);
		return p.parseAndVisitWith(eval).asDouble();
	}

	private Set<String> findDeps(String expression)
	{
		AntlrExpressionParser p = new AntlrExpressionParser(expression);
		ListVariablesInExpr listVars = new ListVariablesInExpr();
		p.parseAndVisitWith(listVars);
		return listVars.getVariablesInExpr();
	}
}