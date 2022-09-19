package lox.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import lox.LoxLanguage;

public class LoxRootNode extends RootNode {
    @Child ExpressionNode node;

    public LoxRootNode(ExpressionNode node, FrameDescriptor frameDescriptor) {
        super(LoxLanguage.getCurrent(), frameDescriptor);
        this.node = node;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return node.executeGeneric(frame);
    }
}
