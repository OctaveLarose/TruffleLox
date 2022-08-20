package lox.nodes.functions;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import lox.LoxLanguage;
import lox.nodes.ExpressionNode;

public class FunctionRootNode extends RootNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child private ExpressionNode functionBody;

    public FunctionRootNode(LoxLanguage loxLanguage, FrameDescriptor frameDescriptor, ExpressionNode functionBody) {
        super(loxLanguage, frameDescriptor);
        this.functionBody = functionBody;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.functionBody.executeGeneric(frame);
    }
}
