package lox.nodes.classes;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.objects.LoxClassInstance;

// A property being either an attribute or a method. Not a fan of that naming so far
public class SetObjectPropertyNode extends ExpressionNode  {
    private final ExpressionNode classExpr;
    private final String attrName;

    public SetObjectPropertyNode(ExpressionNode classExpr, String attrName) {
        this.classExpr = classExpr;
        this.attrName = attrName;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object classObj = this.classExpr.executeGeneric(frame);

        if (!(classObj instanceof LoxClassInstance classInstance))
            throw new RuntimeException("Trying to access an attribute from a non-class");

        return null; // TODO
//        classInstance.setAttribute(this.attrName, value);
//        return value;
    }
}
