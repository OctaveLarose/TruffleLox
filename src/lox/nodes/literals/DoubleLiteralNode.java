package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;

public class DoubleLiteralNode extends LiteralNode {
    private final double value;

    public DoubleLiteralNode(double value) {
        this.value = value;
    }

    public double executeDouble(@SuppressWarnings("unused") VirtualFrame frame) {
        return this.value;
    }

    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }
}
