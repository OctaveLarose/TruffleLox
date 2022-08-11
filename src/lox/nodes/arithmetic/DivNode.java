package lox.nodes.arithmetic;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class DivNode extends BinaryExprNode {
    @Specialization
    public long doLong(long left, long right) {
        return left / right;
    }

    @Specialization
    public double doDouble(double left, double right) {
        return left / right;
    }
}
