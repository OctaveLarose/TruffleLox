package lox.nodes.conditionals;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxTypeSystem;
import lox.LoxTypeSystemGen;
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

        if (LoxTypeSystemGen.asImplicitBoolean(condition.executeGeneric(frame)))
            return consequent.executeGeneric(frame);

        return Nil.INSTANCE;
    }
}
