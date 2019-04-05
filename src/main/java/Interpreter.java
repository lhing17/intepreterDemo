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


    private int toInt(char c) {
        return c - '0';
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
                return new Token(TokenType.PLUS, currentChar);
            }
            if (currentChar == '-') {
                advance();
                return new Token(TokenType.MINUS, currentChar);
            }
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

        if (TokenType.PLUS.equals(op.getType())) {
            eat(TokenType.PLUS);
        } else {
            eat(TokenType.MINUS);
        }

        Token right = currentToken;
        eat(TokenType.INTERGER);
        if (TokenType.PLUS.equals(op.getType())) {
            return (Integer) left.getValue() + (Integer) right.getValue();
        } else {
            return (Integer) left.getValue() - (Integer) right.getValue();

        }
    }
}
