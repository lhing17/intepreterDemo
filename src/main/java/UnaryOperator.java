/**
 * @author G. Seinfeld
 * @date 2019/04/07
 */
public class UnaryOperator extends AbstractSyntaxTree {
    private Token operator;
    private AbstractSyntaxTree expr;

    public UnaryOperator(Token operator, AbstractSyntaxTree expr) {
        this.operator = operator;
        this.expr = expr;
    }

    public Token getOperator() {
        return operator;
    }

    public void setOperator(Token operator) {
        this.operator = operator;
    }

    public AbstractSyntaxTree getExpr() {
        return expr;
    }

    public void setExpr(AbstractSyntaxTree expr) {
        this.expr = expr;
    }
}
