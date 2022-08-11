package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;

public class FalseLiteralNode extends LiteralNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return false;
    }
}
