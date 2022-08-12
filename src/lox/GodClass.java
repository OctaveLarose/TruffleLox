package lox;

import org.graalvm.polyglot.Context;

// Named after the antipattern! Now it just needs to not eventually become the antipattern!
public class GodClass {

    static public void main(String[] args) {
        LoxREPL.runLoop();
    }

    static public Context getContext() {
        Context.Builder contextBuilder = Context.newBuilder(LoxLanguage.LANG_ID)
                .in(System.in)
                .out(System.out)
                .allowAllAccess(true);
        return contextBuilder.build();
    }
}