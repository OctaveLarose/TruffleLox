package lox.nodes.variables;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.ExpressionNode;
import lox.objects.Nil;

// Like its brethren, should not be GlobalVar... and just VariableDecl
public class GlobalVariableDecl extends ExpressionNode {
    private final String varName;
    private final ExpressionNode assignedExpr;

    public GlobalVariableDecl(String varName, ExpressionNode assignedExpr) {
        this.varName = varName;
        this.assignedExpr = assignedExpr;
    }

    public Object executeGeneric(VirtualFrame frame) {
        Object initValue = Nil.INSTANCE;

        if (this.assignedExpr != null)
            initValue = this.assignedExpr.executeGeneric(frame);

        LoxContext context = LoxContext.get(this);
        // TODO: can currently shadow fields, which should be an error!
        context.globalScope.setVar(this.varName, initValue);

        return initValue;
    }
}
