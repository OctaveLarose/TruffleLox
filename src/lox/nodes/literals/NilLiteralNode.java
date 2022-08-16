package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.objects.Nil;

public final class NilLiteralNode extends LiteralNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return Nil.INSTANCE;
    }
}
