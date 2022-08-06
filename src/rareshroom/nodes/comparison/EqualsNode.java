package rareshroom.nodes.comparison;

import rareshroom.nodes.BinaryExprNode;
import rareshroom.nodes.ExpressionNode;

public class EqualsNode extends BinaryExprNode {

    @Child private ExpressionNode left;
    @Child private ExpressionNode right;

    public EqualsNode(ExpressionNode left, ExpressionNode right) {
        super();
        this.left = left;
        this.right = right;
    }

    public boolean doLong() {
        return left.executeLong() == right.executeLong();
    }

    @Override
    public Object executeGeneric() {
        return doLong();
    }
}
