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
     * program ::= PROGRAM variable SEMI block DOT
     * block ::= declarations compound_statement
     * declarations ::= VAR (variable_declaration SEMI)+ | empty
     * variable_declaration ::= ID (COMMA ID)* COLON type_spec
     * type_spec ::= INTEGER
     * compound_statement ::= BEGIN statement_list END
     * statement_list ::= statement | statement SEMI statement_list
     * statement ::= compound_statement | assign_statement | empty
     * assign_statement ::= variable ASSIGN expr
     * empty ::=
     * expr::= term(PLUS|MINUS term)*
     * term::= factor(MUL|INTEGER_DIV|FLOAT_DIV factor)*
     * factor ::= INTEGER_CONST| REAL_CONST | PLUS factor| MINUS factor | LPAREN expr RPAREN | variable
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
        eat(TokenType.PROGRAM);
        Variable variable = variable();
        String programName = (String) variable.getValue();
        eat(TokenType.SEMI);
        Block block = block();
        Program program = new Program(programName, block);
        eat(TokenType.DOT);
        return program;
    }

    private Block block() {
        List<VariableDeclaration> declarations = declarations();
        Compound compound = compoundStatement();
        return new Block(declarations, compound);
    }

    private List<VariableDeclaration> declarations() {
        List<VariableDeclaration> declarations = new ArrayList<>();
        if (TokenType.VAR.equals(currentToken.getType())) {
            eat(TokenType.VAR);
            while (TokenType.ID.equals(currentToken.getType())) {
                List<VariableDeclaration> variableDeclaration = variableDeclaration();
                declarations.addAll(variableDeclaration);
                eat(TokenType.SEMI);
            }
        }
        return declarations;
    }

    private List<VariableDeclaration> variableDeclaration() {
        List<Variable> variables = new ArrayList<>();
        variables.add(new Variable(currentToken));
        eat(TokenType.ID);

        while (TokenType.COMMA.equals(currentToken.getType())) {
            eat(TokenType.COMMA);
            variables.add(new Variable(currentToken));
            eat(TokenType.ID);
        }
        eat(TokenType.COLON);
        Type type = typeSpec();
        List<VariableDeclaration> declarations = new ArrayList<>();
        for (Variable variable : variables) {
            declarations.add(new VariableDeclaration(variable, type));
        }
        return declarations;
    }

    private Type typeSpec() {
        Token token = currentToken;
        if (TokenType.INTEGER.equals(currentToken.getType())) {
            eat(TokenType.INTEGER);
        } else {
            eat(TokenType.REAL);
        }
        return new Type(token);
    }

    private Compound compoundStatement() {
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
        if (TokenType.INTEGER_CONST.equals(currentToken.getType())) {
            eat(TokenType.INTEGER_CONST);
            return new Num(token);
        }
        if (TokenType.REAL_CONST.equals(currentToken.getType())) {
            eat(TokenType.REAL_CONST);
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
        while (TokenType.MUL.equals(currentToken.getType()) || TokenType.INTEGER_DIV.equals(currentToken.getType()) || TokenType.FLOAT_DIV.equals(currentToken.getType())) {
            Token token = currentToken;
            if (TokenType.MUL.equals(currentToken.getType())) {
                eat(TokenType.MUL);
            }
            if (TokenType.INTEGER_DIV.equals(currentToken.getType())) {
                eat(TokenType.INTEGER_DIV);
            }
            if (TokenType.FLOAT_DIV.equals(currentToken.getType())) {
                eat(TokenType.FLOAT_DIV);
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
