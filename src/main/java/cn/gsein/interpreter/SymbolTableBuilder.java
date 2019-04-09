package cn.gsein.interpreter;

import cn.gsein.interpreter.ast.*;
import cn.gsein.interpreter.symbol.Symbol;
import cn.gsein.interpreter.symbol.SymbolTable;
import cn.gsein.interpreter.symbol.VariableSymbol;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class SymbolTableBuilder extends NodeVisitor {
    private static SymbolTable symbolTable = new SymbolTable();

    public Object visitBlock(AbstractSyntaxTree node) {
        if (node instanceof Block) {
            Block block = (Block) node;
            for (VariableDeclaration declaration : block.getDeclarations()) {
                visit(declaration);
            }
            visit(block.getCompound());
        }
        return null;
    }

    public Object visitProgram(AbstractSyntaxTree node) {
        if (node instanceof Program) {
            Program program = (Program) node;
            visit(program.getBlock());
        }
        return null;
    }

    public Object visitBinaryOperator(AbstractSyntaxTree node) {
        if (node instanceof BinaryOperator) {
            BinaryOperator operator = (BinaryOperator) node;
            visit(operator.getLeft());
            visit(operator.getRight());
        }
        return null;
    }

    public Object visitNum(AbstractSyntaxTree node) {
        return null;
    }

    public Object visitUnaryOperator(AbstractSyntaxTree node) {
        if (node instanceof UnaryOperator) {
            UnaryOperator unaryOperator = (UnaryOperator) node;
            visit(unaryOperator.getExpr());
        }
        return null;
    }

    public Object visitCompound(AbstractSyntaxTree node) {
        if (node instanceof Compound) {
            Compound compound = (Compound) node;
            for (AbstractSyntaxTree child : compound.getChildren()) {
                visit(child);
            }
        }
        return null;
    }

    public Object visitNoOp(AbstractSyntaxTree node) {
        return null;
    }

    public Object visitVariableDeclaration(AbstractSyntaxTree node) {
        if (node instanceof VariableDeclaration) {
            VariableDeclaration declaration = (VariableDeclaration) node;
            String typeName = (String) declaration.getType().getValue();
            Symbol typeSymbol = symbolTable.lookup(typeName);
            String variableName = (String) declaration.getVariable().getValue();
            VariableSymbol variableSymbol = new VariableSymbol(variableName, typeSymbol);
            symbolTable.define(variableSymbol);
        }
        return null;
    }

    public Object visitAssign(AbstractSyntaxTree node) {
        if (node instanceof Assign) {
            Assign assign = (Assign) node;
            String variableName = (String) assign.getLeft().getValue();
            Symbol variableSymbol = symbolTable.lookup(variableName);
            if (variableSymbol == null) {
                throw new IllegalStateException("variable not defined");
            }
            visit(assign.getRight());
        }
        return null;
    }

    public Object visitVariable(AbstractSyntaxTree node) {
        if (node instanceof Variable) {
            Variable variable = (Variable) node;
            String variableName = (String) variable.getValue();
            Symbol variableSymbol = symbolTable.lookup(variableName);
            if (variableSymbol == null) {
                throw new IllegalStateException("variable not defined");
            }
        }
        return null;
    }

}
