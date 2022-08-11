package lox;

import lox.nodes.ExpressionNode;
import lox.parser.ParseError;
import lox.parser.Parser;

public class EntryPoint {
    static public void main(String[] args) {
//        LoxREPL.runLoop();
        ExpressionNode expr = null;
        try {
            expr = new Parser("fun returnThreeToTwoArgs(a, b) { return a + b + 3; } returnThree(3, 4) + 1;").parse();
        } catch (ParseError e) {
            throw new RuntimeException(e);
        }
        System.out.println(expr.executeGeneric(null));
    }
}