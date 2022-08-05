package rareshroom.nodes.arithmetic;

import rareshroom.nodes.BinaryExprNode;
import rareshroom.nodes.ExpressionNode;

public class SubstractNode extends BinaryExprNode {
    @Child private ExpressionNode left;
    @Child private ExpressionNode right;

    public SubstractNode(ExpressionNode left, ExpressionNode right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public Object executeGeneric() {
        return executeLong(); // TODO so there shouldn't be any need to implement it as AddNode should be abstract itself, and generate a specialized class
    }

    public long executeLong() {
        return left.executeLong() - right.executeLong();
    }
}
