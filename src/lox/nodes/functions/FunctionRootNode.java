package lox.nodes.functions;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportMessage;
import lox.nodes.ExpressionNode;
import lox.nodes.LoxRootNode;

public class FunctionRootNode extends LoxRootNode implements TruffleObject {
    private final String name;

    @SuppressWarnings("FieldMayBeFinal")
    @Child private ExpressionNode functionBody;

    public FunctionRootNode(FrameDescriptor frameDescriptor, String funName, ExpressionNode functionBody) {
        super(functionBody, frameDescriptor);
        this.name = funName;
        this.functionBody = functionBody;
    }

    @ExportMessage.Ignore
    @Override
    public Object execute(VirtualFrame frame) {
        return this.functionBody.executeGeneric(frame);
    }

    @Override
    public String toString() {
        return "<fn " + name + ">";
    }
}
