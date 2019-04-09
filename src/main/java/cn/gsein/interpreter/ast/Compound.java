package cn.gsein.interpreter.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class Compound extends AbstractSyntaxTree {
    private List<AbstractSyntaxTree> children;

    public List<AbstractSyntaxTree> getChildren() {
        return children;
    }

    public void setChildren(List<AbstractSyntaxTree> children) {
        this.children = children;
    }

    public Compound() {
        children = new ArrayList<>();
    }
}
