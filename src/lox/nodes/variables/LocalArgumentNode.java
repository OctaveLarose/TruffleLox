package lox.nodes.variables;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

public abstract class LocalArgumentNode extends ExpressionNode {

    protected int slotId;

    public LocalArgumentNode(int argIdx) {
        this.slotId = argIdx;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public static abstract class LocalArgumentReadNode extends LocalArgumentNode {
        public LocalArgumentReadNode(int argIdx) {
            super(argIdx);
        }

        @Specialization
        public Object readGeneric(VirtualFrame frame) {
            var arguments = frame.getArguments();
            return arguments[this.slotId];
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public static abstract class LocalArgumentWriteNode extends LocalArgumentNode {
        public LocalArgumentWriteNode(int argIdx) {
            super(argIdx);
        }

        @Specialization
        public Object writeGeneric(VirtualFrame frame, Object value) {
            frame.getArguments()[this.slotId] = value;
            return value;
        }
    }
}
