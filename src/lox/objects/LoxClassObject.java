package lox.objects;

import lox.nodes.functions.FunctionRootNode;

import java.util.Map;

public class LoxClassObject {
    private final String name;
    private final String superclassName;
    private final Map<String, FunctionRootNode> methods;

    public LoxClassObject(String name, String superclassName, Map<String, FunctionRootNode> methods) {
        this.name = name;
        this.superclassName = superclassName; // TODO should be an object and not a name. Easy fix
        this.methods = methods;
    }

    public LoxClassInstance createInstance(Object[] evaluatedArgs) {
        return new LoxClassInstance(this, evaluatedArgs);
    }

    public FunctionRootNode getMethod(String name) {
        return this.methods.getOrDefault(name, null);
    }

    @Override
    public String toString() {
        return name;
    }
}
