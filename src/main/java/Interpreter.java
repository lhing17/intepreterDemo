/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Interpreter {
    private Token currentToken;
    private Lexer lexer;

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    public Interpreter(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = null;
    }


    public void eat(String tokenType) {
        if (tokenType.equals(currentToken.getType())) {
            currentToken = lexer.getNextToken();
        } else {
            throw new IllegalStateException("Error parsing input");
        }
    }

    public Object express() {
        currentToken = lexer.getNextToken();

        Object result = factor();

        while (currentToken != null && (TokenType.PLUS.equals(currentToken.getType()) || TokenType.MINUS.equals(currentToken.getType()))) {
            if (TokenType.PLUS.equals(currentToken.getType())) {
                eat(TokenType.PLUS);
                result = (Integer) result + (Integer) factor();
            } else {
                eat(TokenType.MINUS);
                result = (Integer) result - (Integer) factor();
            }
        }
        return result;
    }

    private Object factor() {
        Token token = currentToken;
        eat(TokenType.INTEGER);
        return token.getValue();
    }
}
