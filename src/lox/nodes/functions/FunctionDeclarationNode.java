package lox.nodes.functions;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.BlockNode;
import lox.nodes.ExpressionNode;

public class FunctionDeclarationNode extends ExpressionNode {

    private String name;
    private final FrameDescriptor frameDescriptor;
    private final BlockNode block;

    private boolean inObject;

    public FunctionDeclarationNode(String funName, FrameDescriptor frameDescriptor, BlockNode funBlock) {
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
        FunctionRootNode rootNode = new FunctionRootNode(frameDescriptor, name, block);

        if (inObject) // If it's in an object - i.e. a method, so not defined in the global scope - we return it and let the object handle it. Not sure about that approach though, kinda bad
            return rootNode;
        else
            loxContext.globalScope.setFunction(this.name, rootNode);

        return rootNode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
