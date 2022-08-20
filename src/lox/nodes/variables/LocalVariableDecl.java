package lox.nodes.variables;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.objects.Nil;

public class LocalVariableDecl extends ExpressionNode {
    private final String varName;

    private final int slotId;
    private final ExpressionNode assignedExpr;

    public LocalVariableDecl(String varName, int slotId, ExpressionNode assignedExpr) {
        this.varName = varName;
        this.slotId = slotId;
        this.assignedExpr = assignedExpr;
    }

    public Object executeGeneric(VirtualFrame frame) {
        Object initValue = Nil.INSTANCE;

        if (this.assignedExpr != null)
            initValue = this.assignedExpr.executeGeneric(frame);

        // TODO: can currently shadow fields, which should be an error!
        frame.setObject(slotId, initValue);

        return initValue;
    }
}
