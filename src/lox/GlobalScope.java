package lox;

import lox.nodes.functions.FunctionRootNode;
import lox.objects.LoxClassObject;

import java.util.HashMap;

public final class GlobalScope {

    static private final HashMap<String, FunctionRootNode> functions = new HashMap<>();

    static private final HashMap<String, LoxClassObject> classes = new HashMap<>();

    public FunctionRootNode getFunction(String name) {
        return functions.get(name);
    }

    public void setFunction(String name, FunctionRootNode functionRootNode) {
        functions.put(name, functionRootNode);
    }

    public LoxClassObject getClass(String name) {
        return classes.get(name);
    }

    public void setClass(String name, LoxClassObject loxClass) {
        classes.put(name, loxClass);
    }
}
