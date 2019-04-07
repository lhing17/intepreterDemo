/**
 * @author G. Seinfeld
 * @date 2019/04/06
 */
public class Parser {
    private Token currentToken;
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    /*
     * Grammar:
     * EXPR ::= TERM (PLUS|MINUS TERM)*
     * TERM ::= FACTOR (MUL|DIV FACTOR)*
     * FACTOR ::= INTEGER | (PLUS|MINUS)FACTOR | LPAREN EXPR RPAREN
     */
    public void eat(String tokenType) {
        if (tokenType.equals(currentToken.getType())) {
            currentToken = lexer.getNextToken();
        } else {
            throw new IllegalStateException("Error parsing input");
        }
    }

    public AbstractSyntaxTree factor() {
        Token token = currentToken;
        if (TokenType.PLUS.equals(currentToken.getType())){
            eat(TokenType.PLUS);
            return new UnaryOperator(token, factor());
        }
        if (TokenType.MINUS.equals(currentToken.getType())){
            eat(TokenType.MINUS);
            return new UnaryOperator(token, factor());
        }
        if (TokenType.INTEGER.equals(currentToken.getType())) {
            eat(TokenType.INTEGER);
            return new Num(token);
        }
        if (TokenType.LPAREN.equals(currentToken.getType())) {
            eat(TokenType.LPAREN);
            AbstractSyntaxTree node = expr();
            eat(TokenType.RPAREN);
            return node;
        }
        throw new IllegalStateException("Error parsing input");
    }

    public AbstractSyntaxTree term() {
        AbstractSyntaxTree node = factor();
        while (TokenType.MUL.equals(currentToken.getType()) || TokenType.DIV.equals(currentToken.getType())) {
            Token token = currentToken;
            if (TokenType.MUL.equals(currentToken.getType())) {
                eat(TokenType.MUL);
            }
            if (TokenType.DIV.equals(currentToken.getType())) {
                eat(TokenType.DIV);
            }
            node = new BinaryOperator(node, token, factor());
        }
        return node;
    }

    public AbstractSyntaxTree expr() {
        AbstractSyntaxTree node = term();
        while (TokenType.PLUS.equals(currentToken.getType()) || TokenType.MINUS.equals(currentToken.getType())) {
            Token token = currentToken;
            if (TokenType.PLUS.equals(currentToken.getType())) {
                eat(TokenType.PLUS);
            }
            if (TokenType.MINUS.equals(currentToken.getType())) {
                eat(TokenType.MINUS);
            }
            node = new BinaryOperator(node, token, term());
        }
        return node;
    }

    public AbstractSyntaxTree parse() {
        return expr();
    }

}
