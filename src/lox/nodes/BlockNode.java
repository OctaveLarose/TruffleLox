package lox.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.List;

public class BlockNode extends ExpressionNode {
    private final List<ExpressionNode> expressions;

    public BlockNode(List<ExpressionNode> expressions) {
        this.expressions = expressions;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        int blockLen = this.expressions.size();

        if (blockLen == 0)
            return null;

        for (int i = 0; i < blockLen - 1; i++) {
            this.expressions.get(i).executeGeneric(frame);
        }

        return this.expressions.get(blockLen - 1).executeGeneric(frame);
    }
}
