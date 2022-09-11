package lox.nodes.loop;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.nodes.literals.TrueLiteralNode;
import lox.objects.Nil;

public class ForNode extends ExpressionNode {

    @Child private ExpressionNode initialization;
    @Child private ExpressionNode termination;
    @Child private ExpressionNode increment;
    @Child private ExpressionNode body;

    public ForNode(ExpressionNode initialization, ExpressionNode termination, ExpressionNode increment, ExpressionNode body) {
        this.initialization = initialization;
        this.termination = termination != null ? termination: new TrueLiteralNode();
        this.increment = increment;
        this.body = body;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        if (initialization != null)
            initialization.executeGeneric(frame);

        while ((boolean) termination.executeGeneric(frame)) {
            body.executeGeneric(frame);
            if (increment != null)
                increment.executeGeneric(frame);
        }
        
        return Nil.INSTANCE;
    }
}
