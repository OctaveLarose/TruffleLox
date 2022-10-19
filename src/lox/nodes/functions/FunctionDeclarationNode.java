package lox.nodes.functions;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.functions.blocks.BlockNode;
import lox.nodes.ExpressionNode;

public class FunctionDeclarationNode extends ExpressionNode {

    private final String name;

    private final int nbrArgs;

    private final BlockNode block;

    private boolean inObject;

    public FunctionDeclarationNode(String funName, int nbrArgs, BlockNode funBlock) {
        this.name = funName;
        this.nbrArgs = nbrArgs;
        this.block = funBlock;
    }

    public void setIsMethod(boolean val) {
        this.inObject = val;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext loxContext = LoxContext.get(this);
        FunctionRootNode rootNode = new FunctionRootNode(this.block.getBlockRootNode().getFrameDescriptor(), name, nbrArgs, block);

        // If it's in an object - i.e. a method, so not defined in the global scope - we return it and let the object handle it. Not sure about that approach though
        if (inObject)
            return rootNode;
        else
            loxContext.globalScope.setFunction(this.name, rootNode);

        return rootNode;
    }

    public String getName() {
        return this.name;
    }
}
