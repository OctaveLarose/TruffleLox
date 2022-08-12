package lox;

import org.graalvm.polyglot.Context;

// Named after the antipattern! Now it just needs to not eventually become the antipattern!
public class GodClass {

    static public void main(String[] args) {
        runREPL();
    }

    static public void runREPL() {
        LoxREPL.runLoop();
    }

    static public void evalString(String inputStr) {
        System.out.println(getContext().eval("tlox", inputStr));
    }

    static public Context getContext() {
        Context.Builder contextBuilder = Context.newBuilder(LoxLanguage.LANG_ID)
                .in(System.in)
                .out(System.out)
                .allowAllAccess(true);
        return contextBuilder.build();
    }
}