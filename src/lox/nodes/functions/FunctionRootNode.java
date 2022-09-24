package lox.nodes.functions;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportMessage;
import lox.nodes.BlockNode;
import lox.nodes.LoxRootNode;

public class FunctionRootNode extends LoxRootNode implements TruffleObject {
    private final String name;

    @SuppressWarnings("FieldMayBeFinal")
    @Child private BlockNode functionBody;

    public FunctionRootNode(FrameDescriptor frameDescriptor, String funName, BlockNode functionBody) {
        super(functionBody.getExpressions(), frameDescriptor);
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
