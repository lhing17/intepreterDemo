package cn.gsein.interpreter;

/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Test {
    public static void main(String[] args) {
        String text = "BEGIN\n" +
                "\n" +
                "    BEGIN\n" +
                "        number := 2;\n" +
                "        a := number;\n" +
                "        b := 10 * a + 10 * number / 4;\n" +
                "        c := a - - b;\n" +
                "    END;\n" +
                "    \n" +
                "    x := 11;\n" +
                "END.";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        Object result = interpreter.interpret();

        System.out.println(result);

        Object s = 1;
        System.out.println(s.getClass().getSimpleName());
    }
}
