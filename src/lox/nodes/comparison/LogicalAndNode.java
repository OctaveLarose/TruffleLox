package lox.nodes.comparison;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class LogicalAndNode extends BinaryExprNode {

    @Specialization
    public boolean doBool(boolean left, boolean right) {
        return left && right;
    }
}
