package cn.gsein.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public class Lexer {
    private String text;
    private int pos;
    private Character currentChar;

    Lexer(String text) {
        this.text = text;
        this.pos = 0;
        this.currentChar = text.charAt(pos);
    }

    private Integer integer() {
        StringBuilder result = new StringBuilder();
        while (currentChar != null && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return Integer.valueOf(result.toString());
    }

    private void advance() {
        if (++pos > text.length() - 1) {
            currentChar = null;
        } else {
            currentChar = text.charAt(pos);
        }
    }

    public Character peek() {
        int peekPos = pos + 1;
        if (peekPos > text.length() - 1) {
            return null;
        } else {
            return text.charAt(peekPos);
        }
    }

    private static final Map<String, Token> RESERVED_KEYWORDS = new HashMap<String, Token>() {{
        put("BEGIN", new Token(TokenType.BEGIN, "BEGIN"));
        put("END", new Token(TokenType.END, "END"));
    }};

    private Token handleId() {
        StringBuilder result = new StringBuilder();
        while (currentChar != null && Character.isLetterOrDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        Token token = RESERVED_KEYWORDS.get(result.toString());
        if (token == null) {
            token = new Token(TokenType.ID, result.toString());
        }
        return token;
    }

    private void skipWhiteSpace() {
        while (currentChar != null && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    Token getNextToken() {
        while (currentChar != null) {
            if (Character.isWhitespace(currentChar)) {
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
                return new Token(TokenType.DIV, '/');
            }
            if (currentChar == '+') {
                advance();
                return new Token(TokenType.PLUS, '+');
            }
            if (currentChar == '-') {
                advance();
                return new Token(TokenType.MINUS, '-');
            }
            if (currentChar == '(') {
                advance();
                return new Token(TokenType.LPAREN, '(');
            }
            if (currentChar == ')') {
                advance();
                return new Token(TokenType.RPAREN, ')');
            }
            if (Character.isLetter(currentChar)) {
                return handleId();
            }
            if (currentChar == ':' && peek() == '=') {
                advance();
                advance();
                return new Token(TokenType.ASSIGN, ":=");
            }
            if (currentChar == ';') {
                advance();
                return new Token(TokenType.SEMI, ';');
            }
            if (currentChar == '.') {
                advance();
                return new Token(TokenType.DOT, '.');
            }
            throw new IllegalStateException("Error parsing input");
        }
        return new Token(TokenType.EOF, null);
    }
}
