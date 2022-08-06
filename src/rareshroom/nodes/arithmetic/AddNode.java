package rareshroom.nodes.arithmetic;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import rareshroom.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class AddNode extends BinaryExprNode {
    @Specialization(rewriteOn = ArithmeticException.class)
    public long executeLong(long left, long right) {
        return Math.addExact(left, right);
    }

    @Specialization(replaces = "executeLong")
    public double executeDouble(double left, double right) {
        return left + right;
    }
}
