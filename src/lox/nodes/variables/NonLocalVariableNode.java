package lox.nodes.variables;

import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached.Shared;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.BlockRootNode;
import lox.nodes.ExpressionNode;

public abstract class NonLocalVariableNode extends ExpressionNode {
    protected String name;

    protected int slotId;

    protected int scopeLevel;

    protected NonLocalVariableNode(String name, int slotId, int scopeLevel) {
        this.name = name;
        this.slotId = slotId;
        this.scopeLevel = scopeLevel;
    }

    public String getName() {
        return this.name;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    protected MaterializedFrame getCorrectFrame(VirtualFrame frame) {
        BlockRootNode correctBlock = (BlockRootNode) frame.getAuxiliarySlot(0);

        for (int i = 0; i < scopeLevel; i++)
            correctBlock = (BlockRootNode) frame.getAuxiliarySlot(0);

        return correctBlock.getFrame();
    }

    public abstract static class VariableReadNode extends NonLocalVariableNode {
        public VariableReadNode(String name, int slotId, int scopeLevel) {
            super(name, slotId, scopeLevel);
        }


        @Specialization(guards = {"correctFrame.isDouble(slotId)"}, rewriteOn = {FrameSlotTypeException.class})
        public final double readDouble(final VirtualFrame frame,
                                       @Shared("all") @Bind("getCorrectFrame(frame)") final MaterializedFrame correctFrame) {
            return correctFrame.getDouble(slotId);
        }

        @Specialization(guards = {"correctFrame.isDouble(slotId)"}, rewriteOn = {FrameSlotTypeException.class})
        public final boolean readBool(final VirtualFrame frame,
                                       @Shared("all") @Bind("getCorrectFrame(frame)") final MaterializedFrame correctFrame) {
            return correctFrame.getBoolean(slotId);
        }

        @Specialization(guards = {"correctFrame.isDouble(slotId)"}, rewriteOn = {FrameSlotTypeException.class})
        public final Object readGeneric(final VirtualFrame frame,
                                        @Shared("all") @Bind("getCorrectFrame(frame)") final MaterializedFrame correctFrame) {
            return correctFrame.getObject(slotId);
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public abstract static class VariableWriteNode extends NonLocalVariableNode {
        public VariableWriteNode(String name, int slotId, int scopeLevel) {
            super(name, slotId, scopeLevel);
        }

        @Specialization
        public double writeDouble(VirtualFrame frame, double value) {
            getCorrectFrame(frame).setDouble(slotId, value);
            return value;
        }

        @Specialization
        public boolean writeBool(VirtualFrame frame, boolean value) {
            getCorrectFrame(frame).setBoolean(slotId, value);
            return value;
        }

        @Specialization(replaces = {"writeDouble", "writeBool"})
        public Object writeGeneric(VirtualFrame frame, Object value) {
            getCorrectFrame(frame).setObject(slotId, value);
            return value;
        }
    }
}
