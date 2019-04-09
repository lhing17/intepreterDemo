package cn.gsein.interpreter;

import cn.gsein.interpreter.ast.AbstractSyntaxTree;

/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Test {
    public static void main(String[] args) {
        String text = "PROGRAM Part10;\n" +
                "VAR\n" +
                "   number     : INTEGER;\n" +
                "   a, b, c, x : INTEGER;\n" +
                "   y          : REAL;\n" +
                "\n" +
                "BEGIN {Part10}\n" +
                "   BEGIN\n" +
                "      number := 2;\n" +
                "      a := number;\n" +
                "      b := 10 * a + 10 * number DIV 4;\n" +
                "      c := a - - b\n" +
                "   END;\n" +
                "   x := 11;\n" +
                "   y := 20 / 7 + 3.14;\n" +
                "   { writeln('a = ', a); }\n" +
                "   { writeln('b = ', b); }\n" +
                "   { writeln('c = ', c); }\n" +
                "   { writeln('number = ', number); }\n" +
                "   { writeln('x = ', x); }\n" +
                "   { writeln('y = ', y); }\n" +
                "END.  {Part10}";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AbstractSyntaxTree tree = parser.parse();
        SymbolTableBuilder builder = new SymbolTableBuilder();
        builder.visit(tree);

        Interpreter interpreter = new Interpreter(tree);
        Object result = interpreter.interpret();

        System.out.println(result);

        Object s = 1;
        System.out.println(s.getClass().getSimpleName());
    }
}
