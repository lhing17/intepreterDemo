package cn.gsein.interpreter.ast;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class VariableDeclaration extends AbstractSyntaxTree {
    private Variable variable;
    private Type type;

    public VariableDeclaration(Variable variable, Type type) {
        this.variable = variable;
        this.type = type;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
