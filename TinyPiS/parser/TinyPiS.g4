// antlr4 -package parser -o antlr-generated  -no-listener parser/TinyPiE.g4
grammar TinyPiS;

prog: varDecls stmt
    ;

varDecls: ('var' IDENTIFIER ';')*
    ;

stmt: '{' stmt* '}'			# compoundStmt
	|'print' expr ';' 		# printStmt
    | IDENTIFIER '=' expr ';'		# assignStmt
    | 'if' '(' expr ')' stmt 'else' stmt #ifStmt
    | 'while' '(' expr ')' stmt	    	# whileStmt
    ;
    
expr: orExpr
      ;

orExpr: orExpr OROP andExpr
	| andExpr
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
	| NOTOP		unaryExpr	# notExpr
	| ADDOP		unaryExpr # not2Expr
	| '(' expr ')'			# parenExpr
	;

NOTOP: '~';
ADDOP: '+'|'-';
MULOP: '*'|'/';
ANDOP: '&';
OROP : '|';

IDENTIFIER: [a-zA-Z_]+[a-zA-Z_0-9]*;
VALUE: [1-9]+[0-9]* | [0];
WS: [ \t\r\n] -> skip;
