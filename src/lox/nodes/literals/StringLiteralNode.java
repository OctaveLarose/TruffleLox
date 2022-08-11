package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;

public class StringLiteralNode extends LiteralNode {
    String value;

    public StringLiteralNode(String value) {
        this.value = value;
    }

    public String executeString(VirtualFrame frame) {
        return this.value;
    }

    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }
}
