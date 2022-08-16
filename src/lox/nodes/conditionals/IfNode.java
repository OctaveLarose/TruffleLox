package lox.nodes.conditionals;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.objects.Nil;


public class IfNode extends ExpressionNode {
    @Child ExpressionNode condition;

    @Child ExpressionNode consequent;

    public IfNode(ExpressionNode condition, ExpressionNode consequent) {
        this.condition = condition;
        this.consequent = consequent;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object conditionValue = condition.executeGeneric(frame);

        if ((conditionValue instanceof Boolean) && (boolean)conditionValue) // TODO: should be evaluated by the type system for truthy/falsey
            return consequent.executeGeneric(frame);

        return Nil.INSTANCE;
    }
}
