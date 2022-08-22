package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;

public class FunctionNameLiteralNode extends LiteralNode {
    String value;

    public FunctionNameLiteralNode(String value) {
        this.value = value;
    }

    public String executeString(VirtualFrame frame) {
        return this.value;
    }

    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }
}
