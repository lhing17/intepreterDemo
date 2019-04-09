package cn.gsein.interpreter;

/**
 * @author G. Seinfeld
 * @date 2019/04/05
 */
public interface TokenType {
    String INTEGER = "INTEGER";
    String PLUS = "PLUS";
    String EOF = "EOF";
    String MINUS = "MINUS";
    String MUL = "MUL";
    String DIV = "DIV";
    String LPAREN = "LPAREN";
    String RPAREN = "RPAREN";
    String BEGIN = "BEGIN";
    String END = "END";
    String DOT = "DOT";
    String ID = "ID";
    String ASSIGN = "ASSIGN";
    String SEMI = "SEMI";
}
