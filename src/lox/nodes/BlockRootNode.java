package lox.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.impl.FrameWithoutBoxing;

public class BlockRootNode extends LoxRootNode {
    //@Child
    private final ExpressionNode expressions;

    public BlockRootNode(SequenceNode expressions, FrameDescriptor frameDescriptor) {
        super(expressions, frameDescriptor);
        this.expressions = expressions;
    }

    public ExpressionNode getExpressions() {
        return expressions;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.expressions.executeGeneric(new FrameWithoutBoxing(this.getFrameDescriptor(), new Object[]{}));
    }
}
