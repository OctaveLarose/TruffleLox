package lox.nodes.functions;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.interop.TruffleObject;
import lox.nodes.BlockNode;
import lox.nodes.LoxRootNode;

public class FunctionRootNode extends LoxRootNode implements TruffleObject {
    private final String name;

    public FunctionRootNode(FrameDescriptor frameDescriptor, String funName, BlockNode functionBody) {
        super(functionBody.getExpressions(), frameDescriptor);
        this.name = funName;
    }

    @Override
    public String toString() {
        return "<fn " + name + ">";
    }
}
