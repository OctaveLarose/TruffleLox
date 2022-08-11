package lox;

import lox.nodes.ExpressionNode;
import lox.nodes.functions.FunctionRootNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        System.out.println(functions);
        Set<Map.Entry<String, Object>> set = functions.entrySet();

        for (Map.Entry<String, Object> me : set) {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
        return functions.get(name);
    }

    public void setFunction(String name, int size, ExpressionNode block) {
        functions.put(name, new FunctionRootNode(LoxLanguage.getCurrent(), block));
    }
}
