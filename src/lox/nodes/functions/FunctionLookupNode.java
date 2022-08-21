package lox.nodes.functions;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.ExpressionNode;

public class FunctionLookupNode extends ExpressionNode {
    private final ExpressionNode lookupExpression;

    public FunctionLookupNode(ExpressionNode expressionNode) {
        this.lookupExpression = expressionNode;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext context = LoxContext.get(this);
        Object caller = this.lookupExpression.executeGeneric(frame);

        if (!(caller instanceof String))
            throw new RuntimeException("Attempting to call something without giving a string identifier");

        FunctionRootNode funRootNode = context.globalScope.getFunction((String) this.lookupExpression.executeGeneric(frame));

        if (funRootNode == null)
            throw new RuntimeException("Function lookup failed");

        return funRootNode;
    }
}
