/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Interpreter {
    private String text;
    private int pos;
    private Token currentToken;

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
    }

    private int toInt(char c) {
        return c - '0';
    }

    public Token getNextToken() {
        String text = this.text;
        if (pos > text.length() - 1) {
            return new Token(TokenType.EOF, null);
        }
        char currentChar = text.charAt(pos);
        if (Character.isDigit(currentChar)) {
            Token token = new Token(TokenType.INTERGER, toInt(currentChar));
            pos++;
            return token;
        }
        if (currentChar == '+') {
            Token token = new Token(TokenType.PLUS, currentChar);
            pos++;
            return token;
        }
        throw new IllegalStateException("Error parsing input");
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

        Token left = currentToken;
        eat(TokenType.INTERGER);

        Token op = currentToken;
        eat(TokenType.PLUS);

        Token right = currentToken;
        eat(TokenType.INTERGER);

        return (Integer) left.getValue() + (Integer) right.getValue();
    }
}
