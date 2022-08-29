package lox.nodes.functions;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import lox.LoxLanguage;
import lox.nodes.ExpressionNode;

public class FunctionRootNode extends RootNode {
    private final String name;

    @SuppressWarnings("FieldMayBeFinal")
    @Child private ExpressionNode functionBody;

    public FunctionRootNode(LoxLanguage loxLanguage, FrameDescriptor frameDescriptor, String funName, ExpressionNode functionBody) {
        super(loxLanguage, frameDescriptor);
        this.name = funName;
        this.functionBody = functionBody;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.functionBody.executeGeneric(frame);
    }

    @Override
    public String toString() {
        return "<fn " + name + ">";
    }
}
