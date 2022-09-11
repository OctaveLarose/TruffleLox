package lox.nodes.variables;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

public abstract class LocalVariableNode extends ExpressionNode {
    protected String name;

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

        @Specialization(guards = {"frame.isDouble(slotId)"}, rewriteOn = {FrameSlotTypeException.class})
        public double readDouble(VirtualFrame frame) throws FrameSlotTypeException {
            return frame.getDouble(slotId);
        }

        @Specialization(guards = {"frame.isBoolean(slotId)"}, rewriteOn = {FrameSlotTypeException.class})
        public boolean readBool(VirtualFrame frame) throws FrameSlotTypeException {
            return frame.getBoolean(slotId);
        }

        @Specialization(replaces = {"readDouble", "readBool"}, rewriteOn = {FrameSlotTypeException.class})
        public Object readGeneric(VirtualFrame frame) throws FrameSlotTypeException {
            return frame.getObject(slotId);
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public abstract static class VariableWriteNode extends LocalVariableNode {
        public VariableWriteNode(String name, int slotId) {
            super(name, slotId);
        }

        @Specialization
        public double writeDouble(VirtualFrame frame, double value) {
            frame.setDouble(slotId, value);
            return value;
        }

        @Specialization
        public boolean writeBool(VirtualFrame frame, boolean value) {
            frame.setBoolean(slotId, value);
            return value;
        }

        @Specialization(replaces = {"writeDouble", "writeBool"})
        public Object writeGeneric(VirtualFrame frame, Object value) {
            frame.setObject(slotId, value);
            return value;
        }
    }
}
