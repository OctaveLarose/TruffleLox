package lox.nodes.comparison;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class GreaterOrEqualsToNode extends BinaryExprNode {

    @Specialization
    public boolean doLong(long left, long right) {
        return left >= right;
    }

    @Specialization
    public boolean doDouble(double left, double right) {
        return left >= right;
    }
}
