package lox;

import lox.nodes.ExpressionNode;
import lox.parser.ParseError;
import lox.parser.Parser;

public class LoxRepl {
    private final static String PROMPT = "> ";

    public static void runLoop() {
        while (true) {
            GlobalIO.print(PROMPT);

            String input = GlobalIO.getInput();
            if (input == null)
                return;

            // We terminate statements if they're unterminated for the sake of convenience
            if (input.charAt(input.length() - 1) != ';')
                input = input.concat(";");

            try {
                ExpressionNode parsedTree = new Parser(input).parse();
                GlobalIO.printLn(parsedTree.executeGeneric());
            } catch (ParseError parseError) {
                GlobalIO.printErrLn(parseError.getMessage());
            }
        }
    }
}
