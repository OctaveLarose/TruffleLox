package lox.nodes.classes;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.ExpressionNode;
import lox.objects.LoxClassObject;

public class SuperExprNode extends ExpressionNode {
    private final String className;

    private final String propertyName;

    public SuperExprNode(String className, String propertyName) {
        this.className = className;
        this.propertyName = propertyName;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext loxContext = LoxContext.get(this);
        LoxClassObject klass = loxContext.globalScope.getClass(className); // TODO need to cache this, or even better cache the actual accessed superclass
        return klass.getSuperMethod(propertyName);
    }
}
