package lang;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VariableTests extends ExpressionTests {
    @Test
    public void variables() {
        assertTrue(runBool("var1 = 42; var1 == 42;"));
//        assertTrue(runBool("var1 = -1000; var1 < 0;")); // TODO fix
    }
}
