package rareshroom.nodes.arithmetic;

import com.oracle.truffle.api.nodes.Node;
import rareshroom.nodes.IntLiteralNode;

public class AddNode extends Node {
    @Child private IntLiteralNode left;
    @Child private IntLiteralNode right;

    private AddNode(IntLiteralNode left, IntLiteralNode right) {
        this.left = left;
        this.right = right;
    }

    public long execute() {
        return Math.addExact(left.execute(), right.execute());
    }
}
