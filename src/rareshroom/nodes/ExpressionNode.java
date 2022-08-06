package rareshroom.nodes;

import com.oracle.truffle.api.nodes.Node;

public abstract class ExpressionNode extends Node {
    public abstract Object executeGeneric();
}
