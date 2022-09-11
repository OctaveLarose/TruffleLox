package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;

public class IntLiteralNode extends LiteralNode {
    private final int value;

    public IntLiteralNode(int value) {
        this.value = value;
    }

    public int executeInt(@SuppressWarnings("unused") VirtualFrame frame) {
        return this.value;
    }

    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }
}
