package lox.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.ExpressionNode;
import lox.nodes.functions.FunctionRootNode;

public class IdentifierNode extends ExpressionNode {
    private final String identifierName;

    public IdentifierNode(String identifierName) {
        this.identifierName = identifierName;
    }

    public String executeString() {
        LoxContext loxContext = LoxContext.get(this);
        FunctionRootNode funNode = loxContext.globalScope.getFunction(identifierName);
        if (funNode != null)
            return funNode.toString();

        // TODO class instances must print "CLASSNAME instance"

        // In general this class calls for specializations, and probably renaming since it's just about PRINTING an identifier so far

        return identifierName;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return executeString();
    }
}
