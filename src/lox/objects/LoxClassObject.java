package lox.objects;

import lox.nodes.functions.FunctionRootNode;

import java.util.Map;

public class LoxClassObject {
    private final String name;
    private final LoxClassObject superclass;
    private final Map<String, FunctionRootNode> methods;

    public LoxClassObject(String name, LoxClassObject superclass, Map<String, FunctionRootNode> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    public LoxClassInstance createInstance(Object[] evaluatedArgs) {
        return new LoxClassInstance(this, evaluatedArgs);
    }

    public FunctionRootNode getMethod(String name) {
        FunctionRootNode method = this.methods.get(name);
        return method != null ? method : this.superclass.getMethod(name);
    }

    public FunctionRootNode getSuperMethod(String name) {
        return this.superclass != null ? this.superclass.getMethod(name) : null;
    }

    @Override
    public String toString() {
        return name;
    }
}
