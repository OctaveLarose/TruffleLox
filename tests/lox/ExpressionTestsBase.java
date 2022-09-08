package lox;

import org.graalvm.polyglot.Context;
import org.junit.Ignore;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Ignore
public abstract class ExpressionTestsBase {
    private static final Context context = Context.create();

    protected boolean runBool(String sourceStr) {
        return context.eval("tlox", sourceStr).asBoolean();
    }

    protected double runDouble(String sourceStr) {
        return context.eval("tlox", sourceStr).asDouble();
    }

    protected String runString(String sourceStr) {
        return context.eval("tlox", sourceStr).asString();
    }

    protected String runWithOutput(String sourceStr) {
        PrintStream standardOut = System.out;
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        context.eval("tlox", sourceStr);

        System.setOut(standardOut);
        return myOut.toString();
    }

    protected boolean returnsNull(String sourceStr) {
        return context.eval("tlox", sourceStr).isNull();
    }
}
