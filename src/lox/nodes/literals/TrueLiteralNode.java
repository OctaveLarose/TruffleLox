package lox.nodes.literals;

public class TrueLiteralNode extends LiteralNode {
    @Override
    public Object executeGeneric() {
        return true;
    }
}
