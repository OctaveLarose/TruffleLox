package rareshroom.nodes;

import java.util.List;

public class SequenceNode extends ExpressionNode {
//    @Children
    private final List<ExpressionNode> expressions;

    public SequenceNode(final List<ExpressionNode> expressions) {
        this.expressions = expressions;
    }

    @Override
    public Object executeGeneric() {
        int expressionsNbr = expressions.size();

        for (int i = 0; i < expressionsNbr - 1; i++) {
            expressions.get(i).executeGeneric();
        }

        return expressions.get(expressionsNbr - 1).executeGeneric();
    }
}
