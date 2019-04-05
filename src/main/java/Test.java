/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Test {
    public static void main(String[] args) {
        String text = "55 - 9 + 8 * 2";
        Lexer lexer = new Lexer(text);
        Interpreter interpreter = new Interpreter(lexer);
        Object result = interpreter.express();

        System.out.println(result);
    }
}
