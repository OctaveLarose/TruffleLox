package lox.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.impl.FrameWithoutBoxing;

public class BlockRootNode extends LoxRootNode {
    //@Child
    private final ExpressionNode expressions;

    private VirtualFrame frame;

    public BlockRootNode(SequenceNode expressions, FrameDescriptor frameDescriptor, BlockNode enclosingScope) {
        super(expressions, frameDescriptor);
        this.expressions = expressions;
        this.frame = new FrameWithoutBoxing(this.getFrameDescriptor(), new Object[]{enclosingScope});
    }

    public ExpressionNode getExpressions() {
        return expressions;
    }

    public VirtualFrame getFrame() {
        return this.frame;
    }

    public Object executeWithOwnFrame() {
        return this.expressions.executeGeneric(this.frame);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.expressions.executeGeneric(frame);
    }
}
