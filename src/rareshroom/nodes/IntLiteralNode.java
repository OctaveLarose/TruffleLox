package rareshroom.nodes;

public class IntLiteralNode extends ExpressionNode {
    long value;

    public IntLiteralNode(long value) {
        this.value = value;
    }

    public long executeLong() {
        return this.value;
    }

    public Object executeGeneric() {
        return this.value;
    }
}
