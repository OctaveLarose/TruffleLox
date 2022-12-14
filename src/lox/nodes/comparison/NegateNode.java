package lox.nodes.comparison;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.UnaryExprNode;

@GenerateNodeFactory
public abstract class NegateNode extends UnaryExprNode {

    @Specialization
    public int doInt(int input) {
        return -input;
    }

    @Specialization
    public double doDouble(double input) {
        return -input;
    }
}
