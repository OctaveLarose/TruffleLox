package lang;

import org.junit.Ignore;
import lox.parser.ParseError;
import lox.parser.Parser;

@Ignore
public abstract class ExpressionTests {
    protected boolean runBool(String sourceStr) {
        try {
            var parsedTree = new Parser(sourceStr).parse();
            return (boolean) parsedTree.executeGeneric();
        } catch (ParseError | ClassCastException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
