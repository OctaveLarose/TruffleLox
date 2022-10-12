package lox.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public class BlockNode extends ExpressionNode {
    private final BlockRootNode blockRootNode;

    public BlockNode(SequenceNode expressions, FrameDescriptor frameDescriptor) {
        this.blockRootNode = new BlockRootNode(expressions, frameDescriptor);
    }

    public ExpressionNode getExpressions() {
        return blockRootNode.getExpressions();
    }

    public BlockRootNode getBlockRootNode() {
        return blockRootNode;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return this.blockRootNode.execute();
    }
}
