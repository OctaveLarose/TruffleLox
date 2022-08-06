package rareshroom.nodes.literals;

// TODO split into an IntLiteralNode and a DoubleLiteralNode to differentiate them
public class NumberLiteralNode extends LiteralNode {
    private final double value;

    public NumberLiteralNode(double value) {
        this.value = value;
    }

    public Object executeGeneric() {
        return this.value;
    }
}
