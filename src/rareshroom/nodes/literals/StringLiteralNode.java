package rareshroom.nodes.literals;

public class StringLiteralNode extends LiteralNode {
    String value;

    public StringLiteralNode(String value) {
        this.value = value;
    }

    public String executeString() {
        return this.value;
    }

    public Object executeGeneric() {
        return this.value;
    }
}
