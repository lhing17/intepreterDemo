package cn.gsein.interpreter;

import cn.gsein.interpreter.ast.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author G. Seinfeld
 * @date 2019/04/06
 */
class Parser {
    private Token currentToken;
    private Lexer lexer;

    Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    /*
     * Grammar:
     * program ::= compound_statement DOT
     * compound_statement ::= BEGIN statement_list END
     * statement_list ::= statement | statement SEMI statement_list
     * statement ::= compound_statement | assign_statement | empty
     * assign_statement ::= variable ASSIGN expr
     * empty ::=
     * expr::= term(PLUS|MINUS term)*
     * term::= factor(MUL|DIV factor)*
     * factor ::= INTEGER | PLUS factor| MINUS factor | LPAREN expr RPAREN | variable
     * variable ::= ID
     */
    private void eat(String tokenType) {
        if (tokenType.equals(currentToken.getType())) {
            currentToken = lexer.getNextToken();
        } else {
            throw new IllegalStateException("Error parsing input");
        }
    }

    private AbstractSyntaxTree program() {
        AbstractSyntaxTree node = compoundStatement();
        eat(TokenType.DOT);
        return node;
    }

    private AbstractSyntaxTree compoundStatement() {
        eat(TokenType.BEGIN);
        List<AbstractSyntaxTree> nodes = statementList();
        eat(TokenType.END);

        Compound compound = new Compound();
        compound.getChildren().addAll(nodes);
        return compound;
    }

    private List<AbstractSyntaxTree> statementList() {
        AbstractSyntaxTree node = statement();
        List<AbstractSyntaxTree> results = new ArrayList<>();
        results.add(node);

        while (TokenType.SEMI.equals(currentToken.getType())) {
            eat(TokenType.SEMI);
            results.add(statement());
        }
        if (TokenType.ID.equals(currentToken.getType())) {
            throw new IllegalStateException("error parsing input.");
        }
        return results;
    }

    private AbstractSyntaxTree statement() {
        if (TokenType.BEGIN.equals(currentToken.getType())) {
            return compoundStatement();
        }
        if (TokenType.ID.equals(currentToken.getType())) {
            return assignmentStatement();
        }
        return empty();
    }

    private AbstractSyntaxTree assignmentStatement() {
        Variable left = variable();
        Token token = currentToken;
        eat(TokenType.ASSIGN);
        AbstractSyntaxTree right = expr();
        return new Assign(left, token, right);
    }

    private Variable variable() {
        Variable node = new Variable(currentToken);
        eat(TokenType.ID);
        return node;
    }

    private AbstractSyntaxTree empty() {
        return new NoOp();
    }

    private AbstractSyntaxTree factor() {
        Token token = currentToken;
        if (TokenType.PLUS.equals(currentToken.getType())) {
            eat(TokenType.PLUS);
            return new UnaryOperator(token, factor());
        }
        if (TokenType.MINUS.equals(currentToken.getType())) {
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
        return variable();
    }

    private AbstractSyntaxTree term() {
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

    private AbstractSyntaxTree expr() {
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

    AbstractSyntaxTree parse() {
        AbstractSyntaxTree node = program();
        if (!TokenType.EOF.equals(currentToken.getType())) {
            throw new IllegalStateException("expecting EOF");
        }
        return node;
    }

}
