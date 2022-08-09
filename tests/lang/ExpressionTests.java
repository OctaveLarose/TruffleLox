package lang;

import org.graalvm.polyglot.Context;
import org.junit.Ignore;

@Ignore
public abstract class ExpressionTests {
    private static final Context context = Context.create();

    protected boolean runBool(String sourceStr) {
        return context.eval("tlox", sourceStr).asBoolean();
    }
}
