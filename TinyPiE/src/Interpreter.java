import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

//import com.sun.tools.corba.se.idl.constExpr.BinaryExpr;

import parser.TinyPiELexer;
import parser.TinyPiEParser;

public class Interpreter extends InterpreterBase {
	int evalExpr(ASTNode ndx, Environment env) {
		if(ndx instanceof ASTBinaryExprNode) {
			ASTBinaryExprNode nd = (ASTBinaryExprNode) ndx;
			if(nd.op.equals("+")) {
				return evalExpr(nd.lhs,env) + evalExpr(nd.rhs, env);
			}else if(nd.op.equals("-")) {
				return evalExpr(nd.lhs,env) - evalExpr(nd.rhs, env);
			}else {
				throw new Error("unknown binary operator");
			}
		}else if(ndx instanceof ASTNumberNode) {
			ASTNumberNode nd = (ASTNumberNode) ndx;
			if(Integer.toString(nd.value).equals(null)) {
				throw new Error("unknown binary operator");
			}else {
				int value = nd.value;
				return value;
			}
		}else if(ndx instanceof ASTVarRefNode) {
			ASTVarRefNode nd = (ASTVarRefNode) ndx;
			if(nd.varName == "x") {
				int varName = 1;
				return varName;
			}else if(nd.varName == "y") {
				int varName = 10;
				return varName;
			}else if(nd.varName == "zß") {
				int varName = -1;
				return varName;
			}else {
				throw new Error("unknown binary operator");
			}
		}
		return 0;
		//throw new Error("Not implemented yet");
	}

	public int eval(ASTNode ast) {
		Environment env = new Environment();
		addGlobalVariable(env, "x", 1);
		addGlobalVariable(env, "y", 10);
		addGlobalVariable(env, "z", -1);		
		return evalExpr(ast, env);
	}

	public static void main(String[] args) throws IOException {
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		TinyPiELexer lexer = new TinyPiELexer(input);
		CommonTokenStream token = new CommonTokenStream(lexer);
		TinyPiEParser parser = new TinyPiEParser(token);
		ParseTree tree = parser.expr();
		ASTGenerator astgen = new ASTGenerator();
		ASTNode ast = astgen.translate(tree);
		Interpreter interp = new Interpreter();
		int answer = interp.eval(ast);
		System.out.println(answer);
	}
}
