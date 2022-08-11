package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;

public final class NilLiteralNode extends LiteralNode {
    public static final NilLiteralNode NIL_VALUE = new NilLiteralNode();

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return NIL_VALUE;
    }
}
