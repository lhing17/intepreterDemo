package cn.gsein.interpreter.ast;

import java.util.List;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class Block extends AbstractSyntaxTree{
    private List<VariableDeclaration> declarations;
    private Compound compound;

    public List<VariableDeclaration> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(List<VariableDeclaration> declarations) {
        this.declarations = declarations;
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public Block(List<VariableDeclaration> declarations, Compound compound) {
        this.declarations = declarations;
        this.compound = compound;
    }
}
