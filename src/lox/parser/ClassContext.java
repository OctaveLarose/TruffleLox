package lox.parser;

public class ClassContext {
    private final String className;

    private final boolean hasSuperClass;

    public ClassContext(String className, boolean hasSuperClass) {
        this.className = className;
        this.hasSuperClass = hasSuperClass;
    }

    public String getName() {
        return this.className;
    }

    public boolean hasSuperClass() {
        return hasSuperClass;
    }
}
