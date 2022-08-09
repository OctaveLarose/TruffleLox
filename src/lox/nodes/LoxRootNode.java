package lox.nodes;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class LoxRootNode extends RootNode {
    protected LoxRootNode(TruffleLanguage<?> language) {
        super(language);
    }

    protected LoxRootNode(TruffleLanguage<?> language, FrameDescriptor frameDescriptor) {
        super(language, frameDescriptor);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return null; // TODO
    }
}
