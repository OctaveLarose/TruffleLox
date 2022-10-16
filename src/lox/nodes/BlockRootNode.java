package lox.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.impl.FrameWithoutBoxing;

public class BlockRootNode extends LoxRootNode {
    //@Child
    private final ExpressionNode expressions;

    private final MaterializedFrame frame;

    public BlockRootNode(SequenceNode expressions, FrameDescriptor frameDescriptor) {
        super(expressions, frameDescriptor);
        this.expressions = expressions;
        this.frame = new FrameWithoutBoxing(this.getFrameDescriptor(), new Object[]{}).materialize();
    }

    public ExpressionNode getExpressions() {
        return expressions;
    }

    public MaterializedFrame getFrame() {
        return this.frame;
    }

    public Object execute() {
        return this.expressions.executeGeneric(this.frame);
    }

    public void setEnclosingScope(BlockRootNode enclosingScope) {
        this.frame.setAuxiliarySlot(0, enclosingScope);
    }
}
