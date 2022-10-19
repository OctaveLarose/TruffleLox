package lox.nodes.classes;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.nodes.ExpressionNode;
import lox.nodes.functions.FunctionDeclarationNode;
import lox.nodes.functions.FunctionRootNode;
import lox.objects.LoxClassObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDeclarationNode extends ExpressionNode {
    private final String name;
    private final String superclassName;

    private final List<FunctionDeclarationNode> methods;

    public ClassDeclarationNode(String className, String extendsClass, List<FunctionDeclarationNode> methods) {
        this.name = className;
        this.superclassName = extendsClass;
        this.methods = methods;
    }


    @Override
    public Object executeGeneric(VirtualFrame frame) {
        LoxContext loxContext = LoxContext.get(this);
        Map<String, FunctionRootNode> methodsMap = new HashMap<>();

        for (FunctionDeclarationNode funDecl: this.methods)
            methodsMap.put(funDecl.getName(), (FunctionRootNode) funDecl.executeGeneric(frame));

        LoxClassObject superclass = loxContext.globalScope.getClass(superclassName);
        LoxClassObject newClass = new LoxClassObject(name, superclass, methodsMap);
        loxContext.globalScope.setClass(this.name, newClass);
        return newClass;
    }
}
