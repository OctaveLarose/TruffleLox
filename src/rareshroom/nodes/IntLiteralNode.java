package rareshroom.nodes;

import com.oracle.truffle.api.nodes.Node;

public class IntLiteralNode extends Node {
    long value;

    IntLiteralNode(long value) {
        this.value = value;
    }
    public long execute() {
        return this.value;
    }
}
