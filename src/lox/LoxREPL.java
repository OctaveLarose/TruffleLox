package lox;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class LoxREPL {
    private final static String PROMPT = "> ";

    private final static Context context = Context.create();

    public static void runLoop() {
        while (true) {
            GlobalIO.print(PROMPT); // TODO can set up Truffle to specify IO for us, I believe

            String input = GlobalIO.getInput();
            if (input == null) {
                return;
            }

            // We terminate statements if they're unterminated for the sake of convenience
            if (input.charAt(input.length() - 1) != ';')
                input = input.concat(";");

            Value value = context.eval("tlox", input);
            GlobalIO.printLn(value);
        }
    }
}
