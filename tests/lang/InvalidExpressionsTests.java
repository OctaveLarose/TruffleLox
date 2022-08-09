package lang;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@Ignore("Not ready yet")
public class InvalidExpressionsTests extends ExpressionTests {
    @Test
    public void invalidSyntax() {
        assertTrue(runBool("1 == 1;"));; //TODO
    }

    @Test
    public void invalidSemantics() {
        ; //TODO probably needs its own class later down the line too
    }
}
