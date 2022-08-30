package lox.objects;

import lox.nodes.classes.ClassInstanceNode;
import lox.nodes.functions.FunctionRootNode;

import java.util.List;

public class LoxClassObject {
    private final String name;
    private final String superclassName;
    private final List<FunctionRootNode> methods;

    public LoxClassObject(String name, String superclassName, List<FunctionRootNode> methods) {
        this.name = name;
        this.superclassName = superclassName;
        this.methods = methods;
    }

    public ClassInstanceNode createInstance(Object[] evaluatedArgs) {
        return new ClassInstanceNode(this, evaluatedArgs);
    }

    public FunctionRootNode getMethod(String name) {
        return this.methods.get(0); // TODO
    }

    @Override
    public String toString() {
        return name;
    }
}
