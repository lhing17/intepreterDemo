package cn.gsein.interpreter.symbol;

import java.util.HashMap;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class SymbolTable extends HashMap<String, Symbol> {

    public SymbolTable(){
        define(new BuiltinTypeSymbol("INTEGER"));
        define(new BuiltinTypeSymbol("REAL"));
    }
    public void define(Symbol symbol) {
        System.out.println("Define: " + symbol);
        put(symbol.name, symbol);
    }

    public Symbol lookup(String name) {
        System.out.println("Lookup: " + name);
        return get(name);
    }
}
