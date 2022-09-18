package lox.basic;

import lox.ExpressionTestsBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StringTests extends ExpressionTestsBase {
    @Test
    public void equality() {
        assertTrue(runBool("\"hello world\" == \"hello world\";"));
        assertTrue(runBool("\"AbcD\" != \"abCd\";"));
    }

    @Ignore(IMPLEMENTED_BUT_NO_EXCEPT_HANDLING)
    @Test
    public void invalidStringComparison() {
        assertTrue(runBool("\"hello world\" >= \"hello world\";")); // throws
    }
}
