package rareshroom;

import rareshroom.nodes.ExpressionNode;
import rareshroom.parser.ParseError;
import rareshroom.parser.ShroomParser;

public class ShroomRepl {
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
                ExpressionNode parsedTree = new ShroomParser(input).parse();
                GlobalIO.printLn(parsedTree.executeGeneric());
            } catch (ParseError parseError) {
                GlobalIO.printErrLn(parseError.getMessage());
            }
        }
    }
}
