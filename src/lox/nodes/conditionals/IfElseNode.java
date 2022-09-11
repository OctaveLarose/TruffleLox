package lox.nodes.conditionals;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxTypeSystemGen;
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
        return LoxTypeSystemGen.asImplicitBoolean(condition.executeGeneric(frame)) ? this.consequent.executeGeneric(frame) : this.alternative.executeGeneric(frame);
    }
}
