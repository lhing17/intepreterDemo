package cn.gsein.interpreter;

import cn.gsein.interpreter.ast.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Interpreter extends NodeVisitor {
    private Parser parser;

    private static final Map<String, Object> GLOBAL_SCOPE = new HashMap<>();

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    public Object visitBinaryOperator(AbstractSyntaxTree node) {
        if (node instanceof BinaryOperator) {
            BinaryOperator op = (BinaryOperator) node;
            if (TokenType.PLUS.equals(op.getOperator().getType())) {
                return (Integer) visit(op.getLeft()) + (Integer) visit(op.getRight());
            }
            if (TokenType.MINUS.equals(op.getOperator().getType())) {
                return (Integer) visit(op.getLeft()) - (Integer) visit(op.getRight());
            }
            if (TokenType.MUL.equals(op.getOperator().getType())) {
                return (Integer) visit(op.getLeft()) * (Integer) visit(op.getRight());
            }
            if (TokenType.DIV.equals(op.getOperator().getType())) {
                return (Integer) visit(op.getLeft()) / (Integer) visit(op.getRight());
            }
        }
        throw new IllegalStateException("error parsing input");
    }

    public Object visitUnaryOperator(AbstractSyntaxTree node) {
        if (node instanceof UnaryOperator) {
            UnaryOperator op = (UnaryOperator) node;
            if (TokenType.PLUS.equals(op.getOperator().getType())) {
                return +(Integer) visit(op.getExpr());
            }
            if (TokenType.MINUS.equals(op.getOperator().getType())) {
                return -(Integer) visit(op.getExpr());
            }
        }
        throw new IllegalStateException("error parsing input");
    }

    public Object visitNum(AbstractSyntaxTree node) {
        if (node instanceof Num) {
            Num num = (Num) node;
            return num.getValue();
        }
        throw new IllegalStateException("error parsing input");
    }

    public Object visitCompound(AbstractSyntaxTree node) {
        if (node instanceof Compound) {
            Compound compound = (Compound) node;
            for (AbstractSyntaxTree child : compound.getChildren()) {
                visit(child);
            }
        }
        return null;
    }

    public Object visitNoOp(AbstractSyntaxTree node) {
        return null;
    }

    public Object visitAssign(AbstractSyntaxTree node) {
        if (node instanceof Assign) {
            Assign assign = (Assign) node;
            String variableName = (String) assign.getLeft().getValue();
            GLOBAL_SCOPE.put(variableName, visit(assign.getRight()));
        }
        return null;
    }

    public Object visitVariable(AbstractSyntaxTree node) {
        if (node instanceof Variable) {
            Variable variable = (Variable) node;
            String variableName = (String) variable.getValue();
            Object value = GLOBAL_SCOPE.get(variableName);
            if (value == null) {
                throw new IllegalStateException("Variable not assigned");
            } else {
                return value;
            }
        }
        throw new IllegalStateException("not a variable");
    }

    public Object interpret() {
        AbstractSyntaxTree tree = parser.parse();
        Object result = visit(tree);
        System.out.println(GLOBAL_SCOPE);
        return result;
    }
}
