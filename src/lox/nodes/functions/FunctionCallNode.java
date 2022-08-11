package lox.nodes.functions;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

import java.util.List;

public class FunctionCallNode extends ExpressionNode {
    private final ExpressionNode callTarget;
    private final List<ExpressionNode> arguments;

    private FunctionDispatchNode dispatchNode;

    public FunctionCallNode(ExpressionNode callTarget, List<ExpressionNode> arguments) {
        this.callTarget = callTarget;
        this.arguments = arguments;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object function = this.callTarget.executeGeneric(frame);
        Object[] evaluatedArgs = new Object[this.arguments.size()];

        int idx = 0;
        for (ExpressionNode expr: this.arguments) {
            evaluatedArgs[idx] = expr.executeGeneric(frame);
        }

        return this.dispatchNode.executeDispatch(function, evaluatedArgs);
    }
}
