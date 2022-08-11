package lox;

import java.util.HashMap;

public final class GlobalScope {
    static private final HashMap<String, Object> variables = new HashMap<>(); // Ugly, but a start to handling state

    public Object setVar(String name, Object obj) {
        variables.put(name, obj);
        return obj;
    }
    
    public Object getVar(String name) {
        return variables.get(name);
    }
}
