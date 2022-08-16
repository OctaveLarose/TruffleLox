package lox.nodes.loop;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.nodes.literals.TrueLiteralNode;
import lox.objects.Nil;

public class ForNode extends ExpressionNode {
    private final ExpressionNode initialization;
    private final ExpressionNode termination;
    private final ExpressionNode increment;
    private final ExpressionNode body;

    public ForNode(ExpressionNode initialization, ExpressionNode termination, ExpressionNode increment, ExpressionNode body) {
        super();
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
