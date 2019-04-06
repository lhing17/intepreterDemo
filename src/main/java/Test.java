/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Test {
    public static void main(String[] args) {
        String text = "55 - (9 + 8 ) * 2";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        Object result = interpreter.interpret();

        System.out.println(result);

        Object s = 1;
        System.out.println(s.getClass().getSimpleName());
    }
}
