package lox.nodes.variables;

import com.oracle.truffle.api.frame.MaterializedFrame;
import lox.nodes.ExpressionNode;

abstract public class NodeWithContext extends ExpressionNode {
    protected MaterializedFrame context;

    public void setContext(MaterializedFrame context) {
        this.context = context;
    }
}
