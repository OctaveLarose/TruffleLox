package rareshroom.nodes.arithmetic;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import rareshroom.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class SubNode extends BinaryExprNode {
    @Specialization
    public long executeLong(long left, long right) {
        return left - right;
    }

    @Specialization
    public double executeDouble(double left, double right) {
        return left - right;
    }
}
