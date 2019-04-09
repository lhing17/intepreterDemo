package cn.gsein.interpreter.ast;

import cn.gsein.interpreter.Token;

/**
 * @author G. Seinfeld
 * @date 2019/04/06
 */
public class BinaryOperator extends AbstractSyntaxTree {
    private AbstractSyntaxTree left;
    private Token operator;
    private AbstractSyntaxTree right;

    public BinaryOperator(AbstractSyntaxTree left, Token operator, AbstractSyntaxTree right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public AbstractSyntaxTree getLeft() {
        return left;
    }

    public void setLeft(AbstractSyntaxTree left) {
        this.left = left;
    }

    public Token getOperator() {
        return operator;
    }

    public void setOperator(Token operator) {
        this.operator = operator;
    }

    public AbstractSyntaxTree getRight() {
        return right;
    }

    public void setRight(AbstractSyntaxTree right) {
        this.right = right;
    }
}
