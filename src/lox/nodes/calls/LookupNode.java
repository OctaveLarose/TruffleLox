package lox.nodes.calls;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.ExpressionNode;
import lox.nodes.functions.FunctionRootNode;
import lox.objects.LoxClassObject;

public abstract class LookupNode extends ExpressionNode {
    private final ExpressionNode lookupExpr;

    private final LoxContext context;

    public LookupNode(ExpressionNode lookupExpr) {
        this.lookupExpr = lookupExpr;
        this.context = LoxContext.get(this);
    }

    @Specialization(rewriteOn = RuntimeException.class)
    public Object executeRootNode(VirtualFrame frame) {
        Object caller = this.lookupExpr.executeGeneric(frame);

        if (caller instanceof FunctionRootNode)
            return caller;

        throw new RuntimeException("Not a function root node, must be an identifier.");
    }

    @Specialization(rewriteOn = RuntimeException.class)
    public FunctionRootNode executeFunction(VirtualFrame frame) {
        Object caller = this.lookupExpr.executeGeneric(frame);

        if (!(caller instanceof String callerString))
            throw new RuntimeException("Attempting to call from something other than an identifier");

        var funNode = context.globalScope.getFunction(callerString);

        if (funNode == null)
            throw new RuntimeException("Not a function.");

        return funNode;
    }

    @Specialization(rewriteOn = RuntimeException.class)
    public LoxClassObject executeClass(VirtualFrame frame) {
        Object caller = this.lookupExpr.executeGeneric(frame);

        if (!(caller instanceof String callerString))
            throw new RuntimeException("Attempting to call something from something other than an identifier");

        var classNode = context.globalScope.getClass(callerString);

        if (classNode == null)
            throw new RuntimeException("Lookup failed, not a class.");

        return classNode;
    }

/*    @Fallback
    public Object lookupFailed(VirtualFrame frame) {
        String callerString = "xdd";
        throw new UnsupportedOperationException("Couldn't look up the caller " + callerString + " (neither a known function nor class)");
    }*/
}
