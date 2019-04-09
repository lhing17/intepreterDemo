package cn.gsein.interpreter.ast;

import cn.gsein.interpreter.Token;

/**
 * @author G. Seinfeld
 * @date 2019/04/06
 */
public class Num extends AbstractSyntaxTree {
    private Token token;
    private Object value;

    public Num(Token token) {
        this.token = token;
        this.value = token.getValue();
    }

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
}
