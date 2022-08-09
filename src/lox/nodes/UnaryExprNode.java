package lox.nodes;

import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild(value = "receiver", type = ExpressionNode.class)
public abstract class UnaryExprNode extends ExpressionNode {
    protected UnaryExprNode() {}
}
