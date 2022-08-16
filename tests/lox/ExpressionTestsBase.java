package lox;

import org.graalvm.polyglot.Context;
import org.junit.Ignore;

@Ignore
public abstract class ExpressionTestsBase {
    private static final Context context = Context.create();

    protected boolean runBool(String sourceStr) {
        return context.eval("tlox", sourceStr).asBoolean();
    }

    protected double runDouble(String sourceStr) {
        return context.eval("tlox", sourceStr).asDouble();
    }

    protected boolean returnsNull(String sourceStr) {
        return context.eval("tlox", sourceStr).isNull();
    }
}
