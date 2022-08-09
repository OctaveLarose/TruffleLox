package lox.nodes;

public abstract class ExpressionNode extends LoxNode {
    public abstract Object executeGeneric();
}
