package lox;

import org.graalvm.polyglot.Context;

// Named after the antipattern! Now it just needs to not eventually become the antipattern!
public class GodClass {

    static public void main(String[] args) {
//        runREPL();
        evalString("""
                class TestClass {  testMethod() {
                    return "wahoo";
                  }
                }

                var a = TestClass();""");
    }

    @SuppressWarnings("unused") // Used for debugging
    static public void runREPL() {
        LoxREPL.runLoop();
    }

    @SuppressWarnings("unused") // Used for debugging
    static public void evalString(String inputStr) {
        var val = getContext().eval("tlox", inputStr);
        System.out.println(val);
    }

    static public Context getContext() {
        Context.Builder contextBuilder = Context.newBuilder(LoxLanguage.LANG_ID)
                .in(GlobalIO.INPUT_STREAM)
                .out(GlobalIO.OUTPUT_STREAM)
                .allowAllAccess(true);
        return contextBuilder.build();
    }
}