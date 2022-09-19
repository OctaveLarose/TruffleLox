package lox.nodes.statements;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.nodes.ReturnException;
import lox.objects.Nil;

public class ReturnStmt extends ExpressionNode {
    private final ExpressionNode value;

    public ReturnStmt(ExpressionNode expr) {
        this.value = expr;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        if (value == null)
            throw new ReturnException(Nil.INSTANCE);

        throw new ReturnException(value.executeGeneric(frame));
    }
}
