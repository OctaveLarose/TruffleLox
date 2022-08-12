package lox.nodes.statements;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.nodes.ReturnException;

public class ReturnStmt extends ExpressionNode {
    private final ExpressionNode value;

    public ReturnStmt(ExpressionNode expr) {
        this.value = expr;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        throw new ReturnException(value.executeGeneric(frame)); // Cascading returns may fail?
    }
}
