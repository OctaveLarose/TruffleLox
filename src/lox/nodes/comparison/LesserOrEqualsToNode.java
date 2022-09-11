package lox.nodes.comparison;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class LesserOrEqualsToNode extends BinaryExprNode {

    @Specialization
    public boolean doInt(int left, int right) {
        return left <= right;
    }

    @Specialization
    public boolean doDouble(double left, int right) {
        return left <= right;
    }

    @Specialization
    public boolean doDouble(int left, double right) {
        return left <= right;
    }
    @Specialization
    public boolean doDouble(double left, double right) {
        return left <= right;
    }
}
