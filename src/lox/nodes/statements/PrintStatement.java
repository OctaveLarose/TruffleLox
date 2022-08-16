package lox.nodes.statements;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import lox.nodes.ExpressionNode;
import lox.objects.Nil;

@GenerateNodeFactory
@NodeChild(value = "valueToPrint", type = ExpressionNode.class)
public abstract class PrintStatement extends ExpressionNode {
    @Specialization
    public Object print(Object value) {
        System.out.println(value);
        return Nil.INSTANCE;
    }
}
