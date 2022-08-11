package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;

public class TrueLiteralNode extends LiteralNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return true;
    }
}
