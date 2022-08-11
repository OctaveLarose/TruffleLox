package lox;

import lox.nodes.ExpressionNode;
import lox.parser.ParseError;
import lox.parser.Parser;
import org.graalvm.polyglot.Context;

import static org.junit.Assert.assertTrue;

public class EntryPoint {
    static public void main(String[] args) {
        LoxREPL.runLoop();
//        ExpressionNode expr = null;
//        try {
//            expr = new Parser("var1 = 42; var1 == 42;").parse();
//        } catch (ParseError e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(expr.executeGeneric(null));

    }
}