package cn.gsein.interpreter.ast;

import cn.gsein.interpreter.Token;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class Variable extends AbstractSyntaxTree{
    private Token token;
    /**
     * holds the variable name
     */
    private Object value;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Variable(Token token) {
        this.token = token;
        this.value = token.getValue();
    }
}
