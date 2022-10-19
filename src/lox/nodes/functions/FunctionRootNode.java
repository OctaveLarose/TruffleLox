package lox.nodes.functions;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.interop.TruffleObject;
import lox.nodes.functions.blocks.BlockNode;
import lox.nodes.LoxRootNode;

public class FunctionRootNode extends LoxRootNode implements TruffleObject {
    private final String name;

    private final int nbrArgs;

    public FunctionRootNode(FrameDescriptor frameDescriptor, String funName, int nbrArgs, BlockNode functionBody) {
        super(functionBody.getExpressions(), frameDescriptor);
        this.name = funName;
        this.nbrArgs = nbrArgs;
    }

    public int getNbrArgs() {
        return this.nbrArgs;
    }

    @Override
    public String toString() {
        return "<fn " + name + ">";
    }
}
