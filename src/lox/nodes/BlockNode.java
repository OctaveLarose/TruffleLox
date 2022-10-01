package lox.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public class BlockNode extends ExpressionNode {
    private final BlockRootNode blockRootNode;

    private final boolean isMethodBlock;

    public BlockNode(SequenceNode expressions, FrameDescriptor frameDescriptor, BlockNode enclosingScope, boolean isMethodBlock) {
        this.blockRootNode = new BlockRootNode(expressions, frameDescriptor, enclosingScope);
        this.isMethodBlock = isMethodBlock;
    }

    public ExpressionNode getExpressions() {
        return blockRootNode.getExpressions();
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        if (isMethodBlock)
            return this.blockRootNode.execute(frame);
        else
            return this.blockRootNode.executeWithOwnFrame();
    }
}
