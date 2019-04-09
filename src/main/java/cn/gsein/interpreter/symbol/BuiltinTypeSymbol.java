package cn.gsein.interpreter.symbol;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class BuiltinTypeSymbol extends Symbol{
    public BuiltinTypeSymbol(String name) {
        super(name, null);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
