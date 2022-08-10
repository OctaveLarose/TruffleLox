package lox.objects;

import lox.nodes.ExpressionNode;

public class Variable {
    final private String name;

    final private ExpressionNode expr;

    public Variable(String name, ExpressionNode expr) {
        this.name = name;
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public ExpressionNode getExpr() {
        return expr;
    }
}
