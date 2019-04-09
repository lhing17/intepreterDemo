package cn.gsein.interpreter;

/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Token {
    private String type;
    private Object value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Token(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "cn.gsein.interpreter.Token{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
