package lox.nodes.arithmetic;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class DivNode extends BinaryExprNode {
    @Specialization
    public double doInt(int left, int right) {
        return ((double) left) / right;
    }

    @Specialization
    public double doDouble(double left, double right) {
        return left / right;
    }
}
