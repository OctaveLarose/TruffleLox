package lox.nodes;

import java.util.HashMap;

public abstract class VariableNode extends ExpressionNode {
    protected String name;
    protected ExpressionNode expr;

    static protected final HashMap<String, Object> variables = new HashMap<>(); // Ugly, but a start to handling state

    protected VariableNode(String name, ExpressionNode expr) {
        this.name = name;
        this.expr = expr;
    }

    // TODO Should be abstract later down the line, same for the Write version
    public static class VariableReadNode extends VariableNode {
        public VariableReadNode(String name) {
            super(name, null);
            this.name = name;
        }

        @Override
        public Object executeGeneric() {
            return variables.get(name);
        }
    }

    public static class VariableWriteNode extends VariableNode {
        public VariableWriteNode(String name, ExpressionNode expr) {
            super(name, expr);
        }

        @Override
        public Object executeGeneric() {
            Object writtenValue = expr.executeGeneric();
            variables.put(name, writtenValue);
            return writtenValue;
        }
    }
}
