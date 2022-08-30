package lox.nodes.classes;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.objects.LoxClassObject;

import java.util.HashMap;

// TODO should be implemented into interop, probably?
public class ClassInstanceNode extends ExpressionNode {

    private HashMap<String, Object> attributes = new HashMap<>();

    public ClassInstanceNode(LoxClassObject classObject, Object[] evaluatedArgs) {
        super();
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return this;
    }
}
