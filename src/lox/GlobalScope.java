package lox;

import lox.nodes.functions.FunctionRootNode;

import java.util.HashMap;

public final class GlobalScope {

    static private final HashMap<String, FunctionRootNode> functions = new HashMap<>();

    public FunctionRootNode getFunction(String name) {
        return functions.get(name);
    }

    public void setFunction(String name, FunctionRootNode functionRootNode) {
        functions.put(name, functionRootNode);
    }
}
