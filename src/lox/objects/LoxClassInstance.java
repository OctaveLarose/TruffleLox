package lox.objects;

import java.util.HashMap;

public class LoxClassInstance {

    private final LoxClassObject classObject;
    private final HashMap<String, Object> properties = new HashMap<>();

    public LoxClassInstance(LoxClassObject classObject, Object[] evaluatedArgs) {
        this.classObject = classObject;
        // TODO should look up and call the class constructor with those evaluated args
    }

    public Object getMethod(String methodName) {
        return this.classObject.getMethod(methodName);
    }

    public Object getProperty(String attrName) {
        if (!this.properties.containsKey(attrName))
            return null;
        return this.properties.get(attrName);
    }

    public void setProperty(String attrName, Object value) {
        this.properties.put(attrName, value);
    }

    @Override
    public String toString() {
        return classObject.toString() + " instance";
    }
}
