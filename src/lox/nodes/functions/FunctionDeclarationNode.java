package lox.nodes.functions;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.LoxLanguage;
import lox.nodes.ExpressionNode;

import java.util.List;

public class FunctionDeclarationNode extends ExpressionNode {

    private final String name;
    private final List<String> parameters;
    private final ExpressionNode block;

    public FunctionDeclarationNode(String funName, List<String> funParameters, ExpressionNode funBlock) {
        super();
        this.name = funName;
        this.parameters = funParameters; // we may only need the -ary of the function therefore an int instead of a list of names
        this.block = funBlock;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext loxContext = LoxContext.get(this);
        loxContext.globalScope.setFunction(this.name, new FunctionRootNode(LoxLanguage.getCurrent(), block));
        return true;
    }
}
