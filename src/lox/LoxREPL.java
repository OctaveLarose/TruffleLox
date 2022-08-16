package lox;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;

public class LoxREPL {
    private final static String PROMPT = "> ";

    private final static Context context = GodClass.getContext();

    public static void runLoop() {
        while (true) {
            GlobalIO.print(PROMPT);

            String input = GlobalIO.getInput();
            if (input == null) {
                return;
            }

            // We terminate statements if they're unterminated for the sake of convenience
            // TODO which fucks up if it's, say a for statement
//            if (input.charAt(input.length() - 1) != ';')
//                input = input.concat(";");

            try {
                context.eval("tlox", input);
//                GlobalIO.printLn(value); // More convenient since it avoids a call to print
            } catch (PolyglotException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
