package lox.nodes;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import lox.LoxContext;
import lox.objects.Variable;

public abstract class VariableNode extends ExpressionNode {
    protected Variable var; // TODO sort out the redundancies with name and ExpressionNode children, bit weird

    protected VariableNode(String name) {
        this.var = new Variable(name, null);
    }

    protected VariableNode(Variable var) {
        this.var = var;
    }

    public Variable getLocalVariable() {
        return this.var;
    }

    public abstract static class VariableReadNode extends VariableNode {
        public VariableReadNode(String name) {
            super(name);
            this.var = new Variable(name, null);
        }

        @Specialization
        public Object read() {
            LoxContext context = LoxContext.get(this);
            return context.globalScope.getVar(this.var.getName());
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public abstract static class VariableWriteNode extends VariableNode {
        public VariableWriteNode(String name) {
            super(name);
        }

        @Specialization
        public Object write(Object value) {
            LoxContext context = LoxContext.get(this);
            context.globalScope.setVar(this.var.getName(), value);
            return value;
        }
    }
}
