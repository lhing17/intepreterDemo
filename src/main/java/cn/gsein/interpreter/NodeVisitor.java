package cn.gsein.interpreter;

import cn.gsein.interpreter.ast.AbstractSyntaxTree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author G. Seinfeld
 * @date 2019/04/06
 */
public class NodeVisitor {
    public Object visit(AbstractSyntaxTree node) {
        Class<?> clazz = node.getClass();
        String name = clazz.getSimpleName();
        try {
            Method method = this.getClass().getDeclaredMethod("visit" + name, AbstractSyntaxTree.class);
            return method.invoke(this, node);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("error parsing input.", e);
        }
    }
}
