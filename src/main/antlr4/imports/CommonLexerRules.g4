lexer grammar CommonLexerRules; 


MUL :   '*' ;
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
POW :   '^' ;

LEQ : '.leq.' ;
GEQ	: '.geq.' ;
LT	: '.lt.'  ;
GT	: '.gt.'  ;
EQ	: '.eq.'  ;
NEQ	: '.neq.' ;
AND: '.and.'  ;
OR:	 '.or.'   ; 

ABS:   'abs'  ;
SQRT:  'sqrt' ;
SIN:   'sin'  ;
COS:   'cos'  ;
TAN:   'tan'  ;
SINH:  'sinh' ;
COSH:  'cosh' ;
TANH:  'tanh' ;
EXP:   'exp'  ;
LOG:   'log'  ;
LN:    'ln'   ;
CEIL:  'ceil' ;
FLOOR: 'floor';
RAND:  'random';

ID  :     [a-zA-Z]+      ; 
WS  :     [ \t]+ -> skip ; 

FLOAT:  DIGIT+ '.' DIGIT* EXPONENT? [Ll]?
    |   DIGIT+ EXPONENT? [Ll]?
    |   '.' DIGIT+ EXPONENT? [Ll]?
    ;
fragment
DIGIT:  '0'..'9' ; 
fragment
EXPONENT :   ('E' | 'e') ('+' | '-')? DIGIT+ ;
