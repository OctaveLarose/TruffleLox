package lox.objects;

import lox.nodes.functions.FunctionDeclarationNode;
import lox.nodes.functions.FunctionRootNode;

import java.util.List;

public class LoxClass {
    private final String name;
    private final String superclassName;
    private final List<FunctionRootNode> methods;

    public LoxClass(String name, String superclassName, List<FunctionRootNode> methods) {
        this.name = name;
        this.superclassName = superclassName;
        this.methods = methods;
    }

    @Override
    public String toString() {
        return name;
    }
}
