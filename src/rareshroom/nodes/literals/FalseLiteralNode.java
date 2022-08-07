package rareshroom.nodes.literals;

public class FalseLiteralNode extends LiteralNode {
    @Override
    public Object executeGeneric() {
        return false;
    }
}
