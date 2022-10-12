package lox.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.impl.FrameWithoutBoxing;

public class BlockRootNode extends LoxRootNode {
    //@Child
    private final ExpressionNode expressions;

    private final VirtualFrame frame;

    public BlockRootNode(SequenceNode expressions, FrameDescriptor frameDescriptor) {
        super(expressions, frameDescriptor);
        this.expressions = expressions;
        this.frame = new FrameWithoutBoxing(this.getFrameDescriptor(), new Object[]{});
    }

    public ExpressionNode getExpressions() {
        return expressions;
    }

    public VirtualFrame getFrame() {
        return this.frame;
    }

    public Object execute() {
        return this.expressions.executeGeneric(this.frame);
    }

    public void setEnclosingScope(BlockRootNode enclosingScope) {
        this.frame.setAuxiliarySlot(0, enclosingScope);
    }
}
