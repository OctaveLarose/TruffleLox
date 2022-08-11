package lox.nodes.statements;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

public class ReturnStmt extends ExpressionNode {
    private final ExpressionNode value;

    public ReturnStmt(ExpressionNode expr) {
        this.value = expr;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return value; // TODO should leave the current scope somehow
    }
}
