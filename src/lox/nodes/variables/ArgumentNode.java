package lox.nodes.variables;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;

// TODO specialize this node
public abstract class ArgumentNode extends ExpressionNode {

    protected int argIdx;

    public ArgumentNode(int argIdx) {
        this.argIdx = argIdx;
    }

    public int getIdx() {
        return this.argIdx;
    }

    public static abstract class ArgumentReadNode extends ArgumentNode {
        public ArgumentReadNode(int argIdx) {
            super(argIdx);
        }

//        @Specialization
//        public boolean readBool(VirtualFrame frame) {
//            var arguments = frame.getArguments();
//            return (boolean) arguments[this.argIdx];
//        }

        @Specialization
        public Object readGeneric(VirtualFrame frame) {
            var arguments = frame.getArguments();
            return arguments[this.argIdx];
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public static abstract class ArgumentWriteNode extends ArgumentNode {
        public ArgumentWriteNode(int argIdx) {
            super(argIdx);
        }

        @Specialization
        public Object writeGeneric(VirtualFrame frame, Object value) {
            frame.getArguments()[this.argIdx] = value;
            return value;
        }
    }
}