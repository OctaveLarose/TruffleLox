package lox.nodes.variables;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

// TODO specialize this node
public class ArgumentReadNode extends ExpressionNode {

    protected int argIdx;

    public ArgumentReadNode(int argIdx) {
        this.argIdx = argIdx;
    }

    public Object executeGeneric(VirtualFrame frame) {
        var arguments = frame.getArguments();
        return arguments[this.argIdx];
    }
}
