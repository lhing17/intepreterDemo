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
        this.currentToken = lexer.getNextToken();
    }


    public void eat(String tokenType) {
        if (tokenType.equals(currentToken.getType())) {
            currentToken = lexer.getNextToken();
        } else {
            throw new IllegalStateException("Error parsing input");
        }
    }

    public Object expr() {
        Object result = term();

        while (currentToken != null && (TokenType.PLUS.equals(currentToken.getType()) || TokenType.MINUS.equals(currentToken.getType()))) {
            if (TokenType.PLUS.equals(currentToken.getType())) {
                eat(TokenType.PLUS);
                result = (Integer) result + (Integer) term();
            } else {
                eat(TokenType.MINUS);
                result = (Integer) result - (Integer) term();
            }
        }
        return result;
    }

    private Object factor() {
        Token token = currentToken;
        if (TokenType.INTEGER.equals(currentToken.getType())){
            eat(TokenType.INTEGER);
            return token.getValue();
        } else if (TokenType.LPAREN.equals(currentToken.getType())) {
            eat(TokenType.LPAREN);
            Object result = expr();
            eat(TokenType.RPAREN);
            return result;
        }
        throw new IllegalStateException("Error parsing input");
    }

    private Object term() {
        Object result = factor();
        while (currentToken != null && (TokenType.MUL.equals(currentToken.getType()) || TokenType.DIV.equals(currentToken.getType()))) {
            if (TokenType.MUL.equals(currentToken.getType())) {
                eat(TokenType.MUL);
                result = (Integer) result * (Integer) factor();
            }
            if (TokenType.DIV.equals(currentToken.getType())) {
                eat(TokenType.DIV);
                result = (Integer) result / (Integer) factor();
            }
        }
        return result;
    }
}
