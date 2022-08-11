package lox.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import lox.LoxLanguage;

public final class LoxRootNode extends RootNode {
    @Child
    ExpressionNode node;

    public LoxRootNode(ExpressionNode node) {
        super(LoxLanguage.getCurrent());
        this.node = node;
    }
    public LoxRootNode(LoxLanguage language) {
        super(language);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return node.executeGeneric(frame);
    }
}
