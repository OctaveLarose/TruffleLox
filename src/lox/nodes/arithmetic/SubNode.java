package lox.nodes.arithmetic;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class SubNode extends BinaryExprNode {
    @Specialization
    public int doInt(int left, int right) {
        return left - right;
    }

    @Specialization
    public double doDouble(double left, double right) {
        return left - right;
    }
}
