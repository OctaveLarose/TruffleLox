package lox.nodes.functions;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.LoxLanguage;
import lox.nodes.ExpressionNode;

public class FunctionDeclarationNode extends ExpressionNode {

    private final String name;
    private final FrameDescriptor frameDescriptor;
    private final ExpressionNode block;

    public FunctionDeclarationNode(String funName, FrameDescriptor frameDescriptor, ExpressionNode funBlock) {
        this.name = funName;
        this.frameDescriptor = frameDescriptor;
        this.block = funBlock;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext loxContext = LoxContext.get(this);
        loxContext.globalScope.setFunction(this.name, new FunctionRootNode(LoxLanguage.getCurrent(), frameDescriptor, block));
        return true;
    }
}
