package lox.nodes.variables;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

public abstract class NonLocalVariableNode extends NodeWithContext {
    protected String name;

    protected int slotId;

    protected NonLocalVariableNode(String name, int slotId) {
        this.name = name;
        this.slotId = slotId;
    }

    public String getName() {
        return this.name;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public abstract static class VariableReadNode extends NonLocalVariableNode {
        public VariableReadNode(String name, int slotId) {
            super(name, slotId);
        }

        @Specialization(guards = {"context.isDouble(slotId)"}, rewriteOn = {FrameSlotTypeException.class})
        public final double readDouble(final VirtualFrame frame) {
            return context.getDouble(slotId);
        }

        @Specialization(guards = {"context.isDouble(slotId)"}, rewriteOn = {FrameSlotTypeException.class})
        public final boolean readBool(final VirtualFrame frame) {
            return context.getBoolean(slotId);
        }

        @Specialization(guards = {"context.isDouble(slotId)"}, rewriteOn = {FrameSlotTypeException.class})
        public final Object readGeneric(final VirtualFrame frame) {
            return context.getObject(slotId);
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public abstract static class VariableWriteNode extends NonLocalVariableNode {
        public VariableWriteNode(String name, int slotId) {
            super(name, slotId);
        }

        @Specialization
        public double writeDouble(VirtualFrame frame, double value) {
            context.setDouble(slotId, value);
            return value;
        }

        @Specialization
        public boolean writeBool(VirtualFrame frame, boolean value) {
            context.setBoolean(slotId, value);
            return value;
        }

        @Specialization(replaces = {"writeDouble", "writeBool"})
        public Object writeGeneric(VirtualFrame frame, Object value) {
            context.setObject(slotId, value);
            return value;
        }
    }
}
