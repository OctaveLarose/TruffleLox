package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;

// TODO split into an IntLiteralNode and a DoubleLiteralNode to differentiate them
public class NumberLiteralNode extends LiteralNode {
    private final double value;

    public NumberLiteralNode(double value) {
        this.value = value;
    }

    public double executeDouble(VirtualFrame frame) {
        return this.value;
    }
    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }
}
