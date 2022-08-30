package lox.nodes.calls;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.nodes.ReturnException;
import lox.nodes.functions.FunctionDispatchNodeGen;
import lox.nodes.functions.FunctionRootNode;
import lox.objects.LoxClassObject;
import lox.objects.LoxFunction;

import java.util.List;

public class CallNode extends ExpressionNode {
    private final ExpressionNode lookupNode;
    private final List<ExpressionNode> arguments;

    public CallNode(LookupNode lookupNode, List<ExpressionNode> arguments) {
        this.lookupNode = lookupNode;
        this.arguments = arguments;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object lookup = this.lookupNode.executeGeneric(frame);

        Object[] evaluatedArgs = evaluateArguments(frame);

        if (lookup instanceof FunctionRootNode function) {
            try {
                return FunctionDispatchNodeGen.create().executeDispatch(new LoxFunction(function.getCallTarget()), evaluatedArgs);
            } catch (ReturnException returnException) {
                return returnException.getResult();
            }
        } else if (lookup instanceof LoxClassObject classObject) {
            return classObject.createInstance(evaluatedArgs);
        } else {
            throw new RuntimeException("should be unreachable");
        }
    }

    private Object[] evaluateArguments(VirtualFrame frame) {
        Object[] evaluatedArgs = new Object[this.arguments.size()];

        int idx = 0;
        for (ExpressionNode expr: this.arguments) {
            evaluatedArgs[idx] = expr.executeGeneric(frame);
            idx++;
        }

        return evaluatedArgs;
    }

}
