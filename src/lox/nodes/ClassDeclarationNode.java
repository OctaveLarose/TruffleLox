package lox.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import lox.LoxContext;
import lox.LoxLanguage;
import lox.nodes.functions.FunctionDeclarationNode;
import lox.nodes.functions.FunctionRootNode;
import lox.objects.LoxClass;

import java.util.ArrayList;
import java.util.List;

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
        List<FunctionRootNode> functionRootNodes = new ArrayList<>();

        for (FunctionDeclarationNode funDecl: this.methods)
            functionRootNodes.add((FunctionRootNode) funDecl.executeGeneric(frame));

        loxContext.globalScope.setClass(this.name, new LoxClass(name, superclassName, functionRootNodes));
        return null;
    }

    public void addMethod(FunctionRootNode rootNode) {
    }
}
