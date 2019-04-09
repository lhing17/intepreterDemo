package cn.gsein.interpreter.symbol;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class Symbol {
    protected String name;
    protected Object type;

    public Symbol(String name, Object type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }
}
