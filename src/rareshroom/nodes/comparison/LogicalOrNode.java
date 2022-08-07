package rareshroom.nodes.comparison;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import rareshroom.nodes.BinaryExprNode;

@GenerateNodeFactory
public abstract class LogicalOrNode extends BinaryExprNode {

    @Specialization
    public boolean doBool(boolean left, boolean right) {
        return left || right; // TODO Need to set up some basic truthy rules, aka make null falsey and everything else truthy
    }
}
