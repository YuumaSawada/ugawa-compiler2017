import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import parser.TinyPiSParser.AndExprContext;
import parser.TinyPiSParser.OrExprContext;
import parser.TinyPiSParser.NotExprContext;
import parser.TinyPiSParser.Not2ExprContext;
import parser.TinyPiSParser.AddExprContext;
import parser.TinyPiSParser.PrintStmtContext;
import parser.TinyPiSParser.AssignStmtContext;
import parser.TinyPiSParser.CompoundStmtContext;
import parser.TinyPiSParser.ExprContext;
import parser.TinyPiSParser.IfStmtContext;
import parser.TinyPiSParser.LiteralExprContext;
import parser.TinyPiSParser.MulExprContext;
import parser.TinyPiSParser.ParenExprContext;
import parser.TinyPiSParser.ProgContext;
import parser.TinyPiSParser.StmtContext;
import parser.TinyPiSParser.VarExprContext;
import parser.TinyPiSParser.WhileStmtContext;

public class ASTGenerator {	
	ASTNode translate(ParseTree ctxx) {
	if (ctxx instanceof ProgContext) {
		ProgContext ctx = (ProgContext) ctxx;
		ArrayList<String> varDecls = new ArrayList<String>();
		for (TerminalNode token: ctx.varDecls().IDENTIFIER())
		varDecls.add(token.getText());
		ASTNode stmt = translate(ctx.stmt());
		return new ASTProgNode(varDecls, stmt);
	} else if (ctxx instanceof CompoundStmtContext) {
		CompoundStmtContext ctx = (CompoundStmtContext) ctxx;
		ArrayList<ASTNode> stmts = new ArrayList<ASTNode>();
		for (StmtContext t: ctx.stmt()) {
		ASTNode n = translate(t);
		stmts.add(n);
		}
		return new ASTCompoundStmtNode(stmts);
	} else if (ctxx instanceof AssignStmtContext) {
		AssignStmtContext ctx = (AssignStmtContext) ctxx;
		String var = ctx.IDENTIFIER().getText();
		ASTNode expr = translate(ctx.expr());
		return new ASTAssignStmtNode(var, expr);
	} else if (ctxx instanceof IfStmtContext) {
		IfStmtContext ctx = (IfStmtContext) ctxx;
		ASTNode expr = translate(ctx.expr());
		ArrayList<ASTNode> stmts = new ArrayList<ASTNode>();
		for (StmtContext t: ctx.stmt()) {
			ASTNode n = translate(t);
			stmts.add(n);
			}
		return new ASTIfStmtNode(expr, stmts.get(0), stmts.get(1));
	} else if (ctxx instanceof WhileStmtContext) {
		WhileStmtContext ctx = (WhileStmtContext) ctxx;
		ASTNode expr = translate(ctx.expr());
		ASTNode stmt = translate(ctx.stmt());
		return new ASTWhileStmtNode(expr, stmt);
	} else if (ctxx instanceof PrintStmtContext) {
		PrintStmtContext ctx = (PrintStmtContext) ctxx;
		ASTNode expr = translate(ctx.expr());
		return new ASTPrintStmtNode(expr);
	} else if (ctxx instanceof ParenExprContext) {
		ParenExprContext ctx = (ParenExprContext) ctxx;
		return translate(ctx.expr());
	}else if (ctxx instanceof ExprContext) {
			ExprContext ctx = (ExprContext) ctxx;
			return translate(ctx.orExpr());
	} else if (ctxx instanceof OrExprContext) {
		OrExprContext ctx = (OrExprContext) ctxx;
		if (ctx.orExpr() == null)
			return translate(ctx.andExpr());
		ASTNode lhs = translate(ctx.orExpr());
		ASTNode rhs = translate(ctx.andExpr());
		return new ASTBinaryExprNode(ctx.OROP().getText(), lhs, rhs);
	} else if (ctxx instanceof AndExprContext) {
		AndExprContext ctx = (AndExprContext) ctxx;
		if (ctx.andExpr() == null)
			return translate(ctx.addExpr());
		ASTNode lhs = translate(ctx.andExpr());
		ASTNode rhs = translate(ctx.addExpr());
		return new ASTBinaryExprNode(ctx.ANDOP().getText(), lhs, rhs);
		} else if (ctxx instanceof AddExprContext) {
			AddExprContext ctx = (AddExprContext) ctxx;
			if (ctx.addExpr() == null)
				return translate(ctx.mulExpr());
			ASTNode lhs = translate(ctx.addExpr());
			ASTNode rhs = translate(ctx.mulExpr());
			return new ASTBinaryExprNode(ctx.ADDOP().getText(), lhs, rhs);
		} else if (ctxx instanceof MulExprContext) {
			MulExprContext ctx = (MulExprContext) ctxx;
			if (ctx.mulExpr() == null)
				return translate(ctx.unaryExpr());
			ASTNode lhs = translate(ctx.mulExpr());
			ASTNode rhs = translate(ctx.unaryExpr());
			return new ASTBinaryExprNode(ctx.MULOP().getText(), lhs, rhs);
		} else if (ctxx instanceof LiteralExprContext) {
			LiteralExprContext ctx = (LiteralExprContext) ctxx;
			int value = Integer.parseInt(ctx.VALUE().getText());
			return new ASTNumberNode(value);
		} else if (ctxx instanceof VarExprContext) {
			VarExprContext ctx = (VarExprContext) ctxx;
			String varName = ctx.IDENTIFIER().getText();
			return new ASTVarRefNode(varName);
		} else if (ctxx instanceof ParenExprContext) {
			ParenExprContext ctx = (ParenExprContext) ctxx;
			return translate(ctx.expr());
		} else if (ctxx instanceof NotExprContext) {
			NotExprContext ctx = (NotExprContext) ctxx;
			ASTNode operand = translate(ctx.unaryExpr());
			return new ASTUnaryExprNode(ctx.NOTOP().getText(), operand);
		} else if (ctxx instanceof Not2ExprContext){
			Not2ExprContext ctx = (Not2ExprContext) ctxx;
			ASTNode operand = translate(ctx.unaryExpr());
			if (operand.equals('+')){
				throw new Error("Unknown parse tree node: "+operand);
			}
			return new ASTUnaryExprNode(ctx.ADDOP().getText(), operand);
		}
		throw new Error("Unknown parse tree node: "+ctxx.getText());		
	}
}
