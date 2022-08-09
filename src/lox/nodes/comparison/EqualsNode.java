package lox.nodes.comparison;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class EqualsNode extends BinaryExprNode {
    @Specialization
    public boolean doDouble(double left, double right) {
        return left == right;
    }
    @Specialization
    public boolean doBoolean(boolean left, boolean right) {
        return left == right;
    }
    @Specialization
    public boolean doLong(long left, long right) {
        return left == right;
    }

    @Specialization
    public boolean doObject(Object left, Object right) {
        return left == right;
    }
}
