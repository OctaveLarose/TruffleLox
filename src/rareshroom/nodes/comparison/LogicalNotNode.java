package rareshroom.nodes.comparison;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import rareshroom.nodes.UnaryExprNode;

@GenerateNodeFactory
public abstract class LogicalNotNode extends UnaryExprNode {

    @Specialization
    public boolean doBool(boolean input) {
        return !input;
    }
}
