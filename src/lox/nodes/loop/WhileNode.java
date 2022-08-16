package lox.nodes.loop;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.objects.Nil;

public class WhileNode extends ExpressionNode {
    private final ExpressionNode condition;
    private final ExpressionNode whileBody;

    public WhileNode(ExpressionNode condition, ExpressionNode whileBody) {
        super();
        this.condition = condition;
        this.whileBody = whileBody;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        while ((boolean) condition.executeGeneric(frame)) {
            this.whileBody.executeGeneric(frame);
        }
        return Nil.INSTANCE;
    }
}
