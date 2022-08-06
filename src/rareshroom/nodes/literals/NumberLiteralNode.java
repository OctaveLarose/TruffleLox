package rareshroom.nodes.literals;

public class NumberLiteralNode extends LiteralNode {
    long value; // TODO make that a double probably

    public NumberLiteralNode(long value) {
        this.value = value;
    }

    public long executeLong() {
        return this.value;
    }

    public Object executeGeneric() {
        return this.value;
    }
}
