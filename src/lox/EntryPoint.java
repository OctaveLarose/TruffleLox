package lox;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class EntryPoint {
    static public void main(String[] args) {
//        LoxREPL.runLoop();
        new LoxLanguage().createContext(null);
        Context context = Context.create();
        Value result = context.eval("TruffleLox",
                "10 + 24 + 56.0");
        assert result.asDouble() == 90.0;
//        assertEquals(90.0, result.asDouble(), 0.0);
    }
}