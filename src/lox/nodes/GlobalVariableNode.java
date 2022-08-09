package lox.nodes;

public class GlobalVariableNode extends ExpressionNode {
    private ExpressionNode expression;

    public GlobalVariableNode(ExpressionNode expr) {
        this.expression = expr;
    }

    @Override
    public Object executeGeneric() {
        return null; // TODO
    }
}
