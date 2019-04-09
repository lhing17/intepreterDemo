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

    private static final Map<String, Token> RESERVED_KEYWORDS = new HashMap<String, Token>() {{
        put("PROGRAM", new Token(TokenType.PROGRAM, "PROGRAM"));
        put("VAR", new Token(TokenType.VAR, "VAR"));
        put("DIV", new Token(TokenType.INTEGER_DIV, "DIV"));
        put("INTEGER", new Token(TokenType.INTEGER, "INTEGER"));
        put("REAL", new Token(TokenType.REAL, "REAL"));
        put("BEGIN", new Token(TokenType.BEGIN, "BEGIN"));
        put("END", new Token(TokenType.END, "END"));

    }};

    Lexer(String text) {
        this.text = text;
        this.pos = 0;
        this.currentChar = text.charAt(pos);
    }

    private Token number() {
        StringBuilder result = new StringBuilder();
        while (currentChar != null && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        if (currentChar != null && currentChar == '.'){
            result.append(currentChar);
            advance();
            while (currentChar != null && Character.isDigit(currentChar)) {
                result.append(currentChar);
                advance();
            }
            return new Token(TokenType.REAL_CONST, Double.valueOf(result.toString()));
        }
        return new Token(TokenType.INTEGER_CONST, Integer.valueOf(result.toString()));
    }

    private void advance() {
        if (++pos > text.length() - 1) {
            currentChar = null;
        } else {
            currentChar = text.charAt(pos);
        }
    }

    private void skipComment() {
        while (currentChar != '}') {
            advance();
        }
        advance();
    }

    public Character peek() {
        int peekPos = pos + 1;
        if (peekPos > text.length() - 1) {
            return null;
        } else {
            return text.charAt(peekPos);
        }
    }


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
                return number();
            }
            if (currentChar == '{') {
                advance();
                skipComment();
                continue;
            }
            if (currentChar == ','){
                advance();
                return new Token(TokenType.COMMA, ',');
            }
            if (currentChar == '*') {
                advance();
                return new Token(TokenType.MUL, '*');
            }
            if (currentChar == '/') {
                advance();
                return new Token(TokenType.FLOAT_DIV, '/');
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
            if (currentChar == ':'){
                advance();
                return new Token(TokenType.COLON, ':');
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
