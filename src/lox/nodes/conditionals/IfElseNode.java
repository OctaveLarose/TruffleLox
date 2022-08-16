package lox.nodes.conditionals;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;


public class IfElseNode extends ExpressionNode {
    @Child ExpressionNode condition;

    @Child ExpressionNode consequent;
    
    @Child ExpressionNode alternative;

    public IfElseNode(ExpressionNode condition, ExpressionNode consequent, ExpressionNode alternative) {
        this.condition = condition;
        this.consequent = consequent;
        this.alternative = alternative;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object conditionValue = condition.executeGeneric(frame);
        boolean isConditionMet = (conditionValue instanceof Boolean) && (boolean) conditionValue;

        return isConditionMet ? this.consequent.executeGeneric(frame) : this.alternative.executeGeneric(frame);
    }
}
