package rareshroom.nodes.literals;

public final class NilLiteralNode extends LiteralNode {
    public static final NilLiteralNode NIL_VALUE = new NilLiteralNode();

    @Override
    public Object executeGeneric() {
        return NIL_VALUE;
    }
}
