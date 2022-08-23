package lox.nodes.functions;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.nodes.ReturnException;
import lox.objects.LoxFunction;

import java.util.List;

public class FunctionCallNode extends ExpressionNode {
    private final ExpressionNode callTarget;
    private final List<ExpressionNode> arguments;

    private final FunctionDispatchNode dispatchNode = FunctionDispatchNodeGen.create();

    public FunctionCallNode(ExpressionNode callTarget, List<ExpressionNode> arguments) {
        this.callTarget = callTarget;
        this.arguments = arguments;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        FunctionRootNode function = (FunctionRootNode) this.callTarget.executeGeneric(frame);
        Object[] evaluatedArgs = new Object[this.arguments.size()];

        int idx = 0;
        for (ExpressionNode expr: this.arguments) {
            evaluatedArgs[idx] = expr.executeGeneric(frame);
            idx++;
        }

        try {
            return this.dispatchNode.executeDispatch(new LoxFunction(function.getCallTarget()), evaluatedArgs);
        } catch (ReturnException returnException) {
            return returnException.getResult();
        }
    }
}
