/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Interpreter {
    private String text;
    private int pos;
    private Token currentToken;
    private Character currentChar;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    public Interpreter(String text) {
        this.text = text;
        this.pos = 0;
        this.currentToken = null;
        this.currentChar = text.charAt(pos);
    }

    public void advance() {
        if (++pos > text.length() - 1) {
            currentChar = null;
        } else {
            currentChar = text.charAt(pos);
        }
    }

    public void skipWhiteSpace() {
        while (currentChar != null && Character.isSpaceChar(currentChar)) {
            advance();
        }
    }

    public Integer integer() {
        StringBuilder result = new StringBuilder();
        while (currentChar != null && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return Integer.valueOf(result.toString());
    }

    public Token getNextToken() {
        while (currentChar != null) {
            if (Character.isSpaceChar(currentChar)) {
                skipWhiteSpace();
                continue;
            }
            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.INTERGER, integer());
            }
            if (currentChar == '+') {
                advance();
                return new Token(TokenType.PLUS, '+');
            }
            if (currentChar == '-') {
                advance();
                return new Token(TokenType.MINUS, '-');
            }
            throw new IllegalStateException("Error parsing input");
        }
        return new Token(TokenType.EOF, null);

    }

    public void eat(String tokenType) {
        if (tokenType.equals(currentToken.getType())) {
            currentToken = getNextToken();
        } else {
            throw new IllegalStateException("Error parsing input");
        }
    }

    public Object express() {
        currentToken = getNextToken();

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

    private Object term() {
        Token token = currentToken;
        eat(TokenType.INTERGER);
        return token.getValue();
    }
}
