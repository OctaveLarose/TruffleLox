package lox.state;

import lox.ExpressionTestsBase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VariableTests extends ExpressionTestsBase {
    @Test
    public void variables() {
        assertTrue(runBool("var l1; l1 = 1.2; l1 == 1.2;"));
        assertTrue(runBool("var l1 = 42; l1 == 42;"));
        assertTrue(runBool("var l1 = -1000; 0 > l1;"));
        assertTrue(runBool("var l1 = 1000; var l2 = l1; l1 == l2; l1 == 1000; l2 == 1000;"));
    }
}
