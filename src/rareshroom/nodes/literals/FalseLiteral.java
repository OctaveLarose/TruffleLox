package rareshroom.nodes.literals;

public class FalseLiteral extends LiteralNode {
    @Override
    public Object executeGeneric() {
        return false;
    }
}
