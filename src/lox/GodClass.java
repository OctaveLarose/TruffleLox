package lox;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;

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
            eval(Files.readString(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Couldn't read file: " + e.getMessage());
            System.exit(1);
        }
    }

    static public void runREPL() {
        LoxREPL.runLoop();
    }

    static public void eval(String inputStr) {
        try {
            getContext().eval("tlox", inputStr);
            System.exit(0);
        } catch (PolyglotException e) {
            // ParseError exception message has the form "java.lang.RuntimeException: lox.parser.error.ParseError: ACTUAL_MESSAGE", so this is a VERY dirty temporary fix
            if (e.getMessage().contains("[line ")) { // i.e. caught a parse exception
                String actualErrorMessage = e.getMessage().substring(e.getMessage().indexOf("[line "));
                System.err.println(actualErrorMessage);
                System.exit(65);
            } else { // Runtime exception
                System.err.println(e.getMessage()); // TODO handle
                System.exit(70);
            }
        }
    }

    @SuppressWarnings("unused") // Used for debugging
    static public void evalPrint(String inputStr) {
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