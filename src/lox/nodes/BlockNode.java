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
        for (ExpressionNode expr: this.expressions) {
            expr.executeGeneric(frame);
        }
        return null;
    }
}
