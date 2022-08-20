package lox.nodes.variables;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

public abstract class LocalVariableNode extends ExpressionNode {
    protected String name; // Not sure that's necessary frankly

    protected int slotId;

    protected LocalVariableNode(String name, int slotId) {
        this.name = name;
        this.slotId = slotId;
    }

    public String getName() {
        return this.name;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public abstract static class VariableReadNode extends LocalVariableNode {
        public VariableReadNode(String name, int slotId) {
            super(name, slotId);
        }

        @Specialization
        public Object read(VirtualFrame frame) {
            // TODO check that it exists?
            return frame.getObject(slotId);
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public abstract static class VariableWriteNode extends LocalVariableNode {
        public VariableWriteNode(String name, int slotId) {
            super(name, slotId);
        }

        @Specialization
        public Object write(VirtualFrame frame, Object value) {
            if (frame.getObject(slotId) == null)
                throw new RuntimeException("Attempted to write to undeclared variable " + this.getName());
            frame.setObject(slotId, value);
            return value;
        }
    }
}