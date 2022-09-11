package lox.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class ExpressionNode extends LoxNode {

    public abstract Object executeGeneric(VirtualFrame frame);
}
