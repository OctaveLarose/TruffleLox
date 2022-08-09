package lang;

import org.junit.Ignore;
import rareshroom.parser.ShroomParser;

@Ignore
public abstract class ExpressionTests {
    protected boolean runBool(String sourceStr) {
        var parsedTree = new ShroomParser(sourceStr).parse();
        return (boolean) parsedTree.executeGeneric();
    }
}
