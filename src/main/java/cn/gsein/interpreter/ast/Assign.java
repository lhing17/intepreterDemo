package cn.gsein.interpreter.ast;

import cn.gsein.interpreter.Token;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class Assign extends AbstractSyntaxTree {
    private Variable left;
    private Token operator;
    private AbstractSyntaxTree right;

    public Variable getLeft() {
        return left;
    }

    public void setLeft(Variable left) {
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

    public Assign(Variable left, Token operator, AbstractSyntaxTree right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}
