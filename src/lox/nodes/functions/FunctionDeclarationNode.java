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

    private boolean inObject;

    public FunctionDeclarationNode(String funName, FrameDescriptor frameDescriptor, ExpressionNode funBlock) {
        this.name = funName;
        this.frameDescriptor = frameDescriptor;
        this.block = funBlock;
    }

    public void setIsMethod(boolean val) {
        this.inObject = val;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext loxContext = LoxContext.get(this);
        FunctionRootNode rootNode = new FunctionRootNode(LoxLanguage.getCurrent(), frameDescriptor, name, block);

        if (inObject) // If it's an object, we return it and let the object handle it. Not sure about that approach though, kinda bad
            return rootNode;
        else
            loxContext.globalScope.setFunction(this.name, rootNode);

        return null;
    }
}
