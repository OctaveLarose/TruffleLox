package lox.objects;

import java.util.HashMap;

// TODO should be implemented into interop, probably?
public class LoxClassInstance {

    private final LoxClassObject classObject;
    private final HashMap<String, Object> attributes = new HashMap<>();

    public LoxClassInstance(LoxClassObject classObject, Object[] evaluatedArgs) {
        this.classObject = classObject;
        // TODO should look up and call the class constructor with those evaluated args
    }

    public Object getMethod(String methodName) {
        return this.classObject.getMethod(methodName);
    }

    public Object getAttribute(String attrName) {
        if (!this.attributes.containsKey(attrName))
            return null;
        return this.attributes.get(attrName);
    }

    public void setAttribute(String attrName, Object value) {
        this.attributes.put(attrName, value);
    }
}
