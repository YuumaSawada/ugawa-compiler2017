// antlr4 -package parser -o antlr-generated  -no-listener parser/TinyPiE.g4
grammar TinyPiE;

expr: andExpr
      ;
      
andExpr: andExpr ANDOP addExpr
	| addExpr
	;

addExpr: addExpr ADDOP mulExpr
	| mulExpr
	;

mulExpr: mulExpr MULOP unaryExpr
	| unaryExpr
	;
	
unaryExpr: VALUE			# literalExpr
	| IDENTIFIER			# varExpr
	| NOTOP				# notExpr
	| '(' expr ')'			# parenExpr
	;

ADDOP: '+'|'-';
MULOP: '*'|'/';
ANDOP: '&'|'|';

IDENTIFIER: 'x'|'y'|'z';
NOTOP:'-'|'~';
VALUE: [1-9][0-9]*;
WS: [ \t\r\n] -> skip;
