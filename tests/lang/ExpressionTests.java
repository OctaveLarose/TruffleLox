package lang;

import org.junit.Ignore;
import rareshroom.parser.ParseError;
import rareshroom.parser.ShroomParser;

@Ignore
public abstract class ExpressionTests {
    protected boolean runBool(String sourceStr) {
        try {
            var parsedTree = new ShroomParser(sourceStr).parse();
            return (boolean) parsedTree.executeGeneric();
        } catch (ParseError | ClassCastException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
