package lox;

import lox.nodes.functions.FunctionRootNode;

import java.util.HashMap;

public final class GlobalScope {

    static private final HashMap<String, Object> functions = new HashMap<>();

    public Object getFunction(String name) {
        return functions.get(name);
    }

    public void setFunction(String name, FunctionRootNode functionRootNode) {
        functions.put(name, functionRootNode);
    }
}
