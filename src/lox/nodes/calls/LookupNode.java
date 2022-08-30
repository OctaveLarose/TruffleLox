package lox.nodes.calls;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.ExpressionNode;
import lox.nodes.functions.FunctionRootNode;
import lox.objects.LoxClassObject;

public class LookupNode extends ExpressionNode {
    private final ExpressionNode lookupExpr;

    public LookupNode(ExpressionNode lookupExpr) {
        this.lookupExpr = lookupExpr;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext context = LoxContext.get(this);
        Object caller = this.lookupExpr.executeGeneric(frame);

        if (!(caller instanceof String callerString))
            throw new RuntimeException("Attempting to call something without giving a string identifier");

        Object lookup = lookupFunction(callerString, context);
        if (lookup != null)
            return lookup;

        lookup = lookupClass(callerString, context);
        if (lookup != null)
            return lookup;


        throw new RuntimeException("Couldn't look up the caller " + callerString + " (neither a known function nor class");
    }

    private FunctionRootNode lookupFunction(String callerString, LoxContext context) {
        return context.globalScope.getFunction(callerString); // TODO those two should be specializations executeFunction() and executeClass(), context probably cached?
    }

    private LoxClassObject lookupClass(String callerString, LoxContext context) {
        return context.globalScope.getClass(callerString);
    }
}
