/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Test {
    public static void main(String[] args) {
        String text = "55 - 9 + 8";
        Interpreter interpreter = new Interpreter(text);
        Object result = interpreter.express();

        System.out.println(result);
    }
}
