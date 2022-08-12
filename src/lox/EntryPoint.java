package lox;

import org.graalvm.polyglot.Context;

public class EntryPoint {

    static public void main(String[] args) {
//        LoxREPL.runLoop();
        System.out.println(getContext().eval(LoxLanguage.LANG_ID, "fun returnThreeToTwoArgs(a, b) { return 1 + 2 + 3; } returnThreeToTwoArgs(3, 4) + 1;"));
//        System.out.println(getContext().eval(LoxLanguage.LANG_ID, "fun returnThreeToTwoArgs(a, b) { return a + b + 3; } returnThreeToTwoArgs(3, 4) + 1;"));
    }

    static public Context getContext() {
        Context.Builder contextBuilder = Context.newBuilder(LoxLanguage.LANG_ID)
                .in(System.in)
                .out(System.out)
                .allowAllAccess(true);
        return contextBuilder.build();
    }
}