package parser.test;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class TextUtils {
	static public String funcCall(String ... args) {
		String pars = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), ',');
		return args[0] + parenthesize(pars);
	}

	static public String parenthesize(String arg) {
		return "(" + arg + ")";
	}

	static public String bracketize(String arg) {
		return "{" + arg + "}";
	}

	public static String latexFuncCall(String ... args) {
		String pars = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), ',');
		return args[0] + bracketize(pars);
	}
}