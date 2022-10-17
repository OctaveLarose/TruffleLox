package lox.nodes.variables;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

public abstract class NonLocalArgumentNode extends NodeWithContext {

    protected int slotId;

    public NonLocalArgumentNode(int argIdx) {
        this.slotId = argIdx;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public static abstract class NonLocalArgumentReadNode extends NonLocalArgumentNode {
        public NonLocalArgumentReadNode(int argIdx) {
            super(argIdx);
        }

        @Specialization
        public Object readGeneric(VirtualFrame frame) {
            return context.getArguments()[this.slotId];
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public static abstract class NonLocalArgumentWriteNode extends NonLocalArgumentNode {
        public NonLocalArgumentWriteNode(int argIdx) {
            super(argIdx);
        }

        @Specialization
        public Object writeGeneric(VirtualFrame frame, Object value) {
            context.getArguments()[this.slotId] = value;
            return value;
        }
    }
}
