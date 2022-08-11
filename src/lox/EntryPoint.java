package lox;

import lox.nodes.ExpressionNode;
import lox.parser.ParseError;
import lox.parser.Parser;

public class EntryPoint {
    static public void main(String[] args) {
//        LoxREPL.runLoop();
        ExpressionNode expr = null;
        try {
            expr = new Parser("functionCall(1, 1 + 42, \"wow\");").parse();
        } catch (ParseError e) {
            throw new RuntimeException(e);
        }
        System.out.println(expr.executeGeneric(null));
    }
}