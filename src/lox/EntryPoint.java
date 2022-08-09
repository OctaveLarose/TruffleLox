package lox;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class EntryPoint {
    static public void main(String[] args) {
//        LoxREPL.runLoop();
        new LoxLanguage().createContext(null);
        Context context = Context.create();
        Value result = context.eval("tlox",
                "10 + 24 + 56.0;");
        assert result.asDouble() == 90.0;
    }
}