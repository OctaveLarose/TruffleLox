package lox.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class ExpressionNode extends LoxNode {
//    public abstract Object executeLong(VirtualFrame frame);

//    public abstract Object executeDouble(VirtualFrame frame);

    public abstract Object executeGeneric(VirtualFrame frame);
}
