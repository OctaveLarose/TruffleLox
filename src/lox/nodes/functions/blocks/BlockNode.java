package lox.nodes.functions.blocks;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.nodes.SequenceNode;

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
