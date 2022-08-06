package rareshroom.nodes.literals;

public class TrueLiteral extends LiteralNode {
    @Override
    public Object executeGeneric() {
        return true;
    }
}
