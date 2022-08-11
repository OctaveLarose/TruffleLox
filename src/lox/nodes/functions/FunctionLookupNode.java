package lox.nodes.functions;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.ExpressionNode;

public class FunctionLookupNode extends ExpressionNode {

    private final String functionName;

    public FunctionLookupNode(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext context = LoxContext.get(this);
        return context.globalScope.getFunction(this.functionName);
    }
}
