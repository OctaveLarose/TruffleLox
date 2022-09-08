package lox.nodes.classes;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import lox.nodes.ExpressionNode;
import lox.objects.LoxClassInstance;

// A property being either an attribute or a method. Not a fan of that naming so far
public abstract class ObjectPropertyNode extends ExpressionNode  {
    protected final ExpressionNode classExpr;

    protected final String propertyName;

    protected ObjectPropertyNode(ExpressionNode classExpr, String propertyName) {
        this.classExpr = classExpr;
        this.propertyName = propertyName;
    }

    public ExpressionNode getClassExpr() {
        return this.classExpr;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public abstract static class ObjectPropertyReadNode extends ObjectPropertyNode {

        public ObjectPropertyReadNode(ExpressionNode classExpr, String attrName) {
            super(classExpr, attrName);
        }

        @Specialization
        public Object read(VirtualFrame frame) {
            Object classObj = this.classExpr.executeGeneric(frame);

            if (!(classObj instanceof LoxClassInstance classInstance))
                throw new RuntimeException("Trying to access an attribute from a non-class");

            Object property = classInstance.getProperty(this.propertyName);
            if (property != null)
                return property;

            Object method = classInstance.getMethod(this.propertyName);
            if (method != null)
                return method;

            throw new RuntimeException("Method/field does not exist: " + this.propertyName);
        }
    }

    @NodeChild(value = "assignmentExpr", type = ExpressionNode.class)
    public abstract static class ObjectPropertyWriteNode extends ObjectPropertyNode {

        public ObjectPropertyWriteNode(ExpressionNode classExpr, String attrName) {
            super(classExpr, attrName);
        }

        @Specialization
        public Object write(VirtualFrame frame, Object value) {
            Object classObj = this.classExpr.executeGeneric(frame);

            if (!(classObj instanceof LoxClassInstance classInstance))
                throw new RuntimeException("Trying to access an attribute from a non-class");

            classInstance.setProperty(propertyName, value);

            return value;
        }
    }
}
