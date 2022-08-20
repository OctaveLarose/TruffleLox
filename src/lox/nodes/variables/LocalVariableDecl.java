package lox.nodes.variables;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.objects.Nil;

public class LocalVariableDecl extends ExpressionNode {

    private final int slotId;
    private final ExpressionNode assignedExpr;

    public LocalVariableDecl(int slotId, ExpressionNode assignedExpr) {
        this.slotId = slotId;
        this.assignedExpr = assignedExpr;
    }

    public LocalVariableDecl(int slotId) {
        this(slotId, null);
    }

    public Object executeGeneric(VirtualFrame frame) {
        Object initValue = Nil.INSTANCE;

        if (this.assignedExpr != null)
            initValue = this.assignedExpr.executeGeneric(frame);

        frame.setObject(slotId, initValue);

        return initValue;
    }
}
