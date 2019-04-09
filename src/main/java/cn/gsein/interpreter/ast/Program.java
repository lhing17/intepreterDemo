package cn.gsein.interpreter.ast;

/**
 * @author G. Seinfeld
 * @date 2019/04/09
 */
public class Program extends AbstractSyntaxTree{
    private String name;
    private Block block;

    public Program(String name, Block block) {
        this.name = name;
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
