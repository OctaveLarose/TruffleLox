package rareshroom.nodes;

import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild(value = "receiver", type = ExpressionNode.class)
@NodeChild(value = "argument", type = ExpressionNode.class)
public abstract class BinaryExprNode extends ExpressionNode {
    protected BinaryExprNode() {}
}
