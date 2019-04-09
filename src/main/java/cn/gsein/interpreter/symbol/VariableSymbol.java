package cn.gsein.interpreter.symbol;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class VariableSymbol extends Symbol{
    public VariableSymbol(String name, Object type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "<"+name+":"+type+">";
    }
}
