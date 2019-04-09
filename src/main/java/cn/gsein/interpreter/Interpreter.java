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

    public Object visitProgram(AbstractSyntaxTree node) {
        if (node instanceof Program) {
            Program program = (Program) node;
            return visit(program.getBlock());
        }
        throw new IllegalStateException("error parsing input");
    }

    public Object visitBlock(AbstractSyntaxTree node) {
        if (node instanceof Block) {
            Block block = (Block) node;
            for (VariableDeclaration declaration : block.getDeclarations()) {
                visit(declaration);
            }
            visit(((Block) node).getCompound());
        }
        return null;
    }

    public Object visitVariableDeclaration(AbstractSyntaxTree node) {
        return null;
    }

    public Object visitType(AbstractSyntaxTree node) {
        return null;
    }

    public Object visitBinaryOperator(AbstractSyntaxTree node) {
        if (node instanceof BinaryOperator) {
            BinaryOperator op = (BinaryOperator) node;
            Object left = visit(op.getLeft());
            Object right = visit(op.getRight());
            Double leftDouble = Double.valueOf(String.valueOf(visit(op.getLeft())));
            Double rightDouble = Double.valueOf(String.valueOf(visit(op.getRight())));
            if (TokenType.PLUS.equals(op.getOperator().getType())) {
                if (left instanceof Integer && right instanceof Integer) {
                    return (Integer) left + (Integer)right;
                } else{
                    return leftDouble + rightDouble;
                }
            }

            if (TokenType.MINUS.equals(op.getOperator().getType())) {
                if (left instanceof Integer && right instanceof Integer) {
                    return (Integer) left - (Integer)right;
                } else{
                    return leftDouble - rightDouble;
                }
            }
            if (TokenType.MUL.equals(op.getOperator().getType())) {
                if (left instanceof Integer && right instanceof Integer) {
                    return (Integer) left * (Integer)right;
                } else{
                    return leftDouble * rightDouble;
                }
            }
            if (TokenType.INTEGER_DIV.equals(op.getOperator().getType())) {
                return (Integer) visit(op.getLeft()) / (Integer) visit(op.getRight());
            }
            if (TokenType.FLOAT_DIV.equals(op.getOperator().getType())) {
                return leftDouble / rightDouble;
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
