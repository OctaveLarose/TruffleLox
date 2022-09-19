package lox;

import org.graalvm.polyglot.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// Named after the antipattern! Now it just needs to not eventually become the antipattern!
public class GodClass {

    static public void main(String[] args) {
        if (args.length != 0) {
            runFile(args[0]);
        } else {
            runREPL();
        }
    }

    static public void runFile(String filePath) {
        try {
            getContext().eval("tlox", Files.readString(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    @SuppressWarnings("unused") // Used for debugging
    static public void runREPL() {
        LoxREPL.runLoop();
    }

    @SuppressWarnings("unused") // Used for debugging
    static public void eval(String inputStr) {
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