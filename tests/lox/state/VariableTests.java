package lox.state;

import lox.ExpressionTestsBase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VariableTests extends ExpressionTestsBase {
    @Test
    public void variables() {
        assertTrue(runBool("var1 = 42; var1 == 42;"));
        assertTrue(runBool("var1 = -1000; 0 > var1;"));
        assertTrue(runBool("var1 = var2 = 1000; var1 == var2; var1 == 1000; var2 == 1000;"));
    }
}
