package lox.nodes.variables;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.BlockRootNode;
import lox.nodes.ExpressionNode;

public abstract class NonLocalArgumentNode extends ExpressionNode {

    protected int slotId;

    protected int scopeLevel;

    public NonLocalArgumentNode(int argIdx, int scopeLevel) {
        this.slotId = argIdx;
        this.scopeLevel = scopeLevel;
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

    public static abstract class NonLocalArgumentReadNode extends NonLocalArgumentNode {
        public NonLocalArgumentReadNode(int argIdx, int scopeLevel) {
            super(argIdx, scopeLevel);
        }

        @Specialization
        public Object readGeneric(VirtualFrame frame) {
            return getCorrectFrame(frame).getArguments()[this.slotId];
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public static abstract class NonLocalArgumentWriteNode extends NonLocalArgumentNode {
        public NonLocalArgumentWriteNode(int argIdx, int scopeLevel) {
            super(argIdx, scopeLevel);
        }

        @Specialization
        public Object writeGeneric(VirtualFrame frame, Object value) {
            getCorrectFrame(frame).getArguments()[this.slotId] = value;
            return value;
        }
    }
}
