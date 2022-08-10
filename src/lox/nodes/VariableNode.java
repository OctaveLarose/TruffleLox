package lox.nodes;

import lox.objects.Variable;

import java.util.HashMap;

public abstract class VariableNode extends ExpressionNode {
    protected Variable var;

    static protected final HashMap<String, Object> variables = new HashMap<>(); // Ugly, but a start to handling state

    protected VariableNode(String name) {
        this.var = new Variable(name, null);
    }

    protected VariableNode(Variable var) {
        this.var = var;
    }

    public Variable getLocalVariable() {
        return this.var;
    }

    // TODO Should be abstract later down the line, same for the Write version
    public static class VariableReadNode extends VariableNode {
        public VariableReadNode(String name) {
            super(name);
            this.var = new Variable(name, null);
        }

        @Override
        public Object executeGeneric() {
            return variables.get(this.var.getName());
        }
    }

    public static class VariableWriteNode extends VariableNode {
        public VariableWriteNode(Variable var) {
            super(var);
        }

        @Override
        public Object executeGeneric() {
            Object writtenValue = this.var.getExpr().executeGeneric();
            variables.put(this.var.getName(), writtenValue);
            return writtenValue;
        }
    }
}
