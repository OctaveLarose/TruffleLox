package lox.nodes.arithmetic;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class MultNode extends BinaryExprNode {
    @Specialization(rewriteOn = ArithmeticException.class)
    public long executeLong(long left, long right) {
        return Math.multiplyExact(left, right);
    }

    @Specialization
    public double executeDouble(double left, double right) {
        return left * right;
    }
}
