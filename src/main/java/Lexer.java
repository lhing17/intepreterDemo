/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Lexer {
    private String text;
    private int pos;
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

    public Character getCurrentChar() {
        return currentChar;
    }

    public void setCurrentChar(Character currentChar) {
        this.currentChar = currentChar;
    }

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
        this.currentChar = text.charAt(pos);
    }

    public Integer integer() {
        StringBuilder result = new StringBuilder();
        while (currentChar != null && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return Integer.valueOf(result.toString());
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

    public Token getNextToken() {
        while (currentChar != null) {
            if (Character.isSpaceChar(currentChar)) {
                skipWhiteSpace();
                continue;
            }
            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.INTEGER, integer());
            }
            if (currentChar == '*') {
                advance();
                return new Token(TokenType.MUL, '*');
            }
            if (currentChar == '/') {
                advance();
                return new Token(TokenType.MUL, '/');
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
}
