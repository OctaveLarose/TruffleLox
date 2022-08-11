package lox;

import java.util.HashMap;

public final class GlobalScope {
    static private final HashMap<String, Object> variables = new HashMap<>();

    static private final HashMap<String, Object> functions = new HashMap<>();

    public Object setVar(String name, Object obj) {
        variables.put(name, obj);
        return obj;
    }
    
    public Object getVar(String name) {
        return variables.get(name);
    }

    public Object getFunction(String name) {
        return functions.get(name);
    }

}
