package lox;

import org.graalvm.polyglot.Context;
import org.junit.Ignore;

@Ignore
public abstract class ExpressionTestsBase {
    private static final Context context = Context.create();

    protected boolean runBool(String sourceStr) {
        return context.eval("tlox", sourceStr).asBoolean();
    }
}
