/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Interpreter extends NodeVisitor {
    private Parser parser;

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

    public Object interpret() {
        AbstractSyntaxTree tree = parser.parse();
        return visit(tree);
    }
}
