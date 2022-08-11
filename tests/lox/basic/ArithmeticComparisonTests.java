package lox.basic;

import lox.ExpressionTestsBase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ArithmeticComparisonTests extends ExpressionTestsBase {
    @Test
    public void notEquals() {
        assertTrue(runBool("1 != 2;"));
        assertTrue(runBool("163 * 5 + 100 / 4 - 12 * 2 + 145 != 10000000000;"));
    }

    @Test
    public void greater() {
        assertTrue(runBool("1 < 2;"));
        assertTrue(runBool("42 <= 43;"));
        assertTrue(runBool("43 <= 43;"));
        assertTrue(runBool("42.00001 <= 42.001;"));
    }

    @Test
    public void lesser() {
        assertTrue(runBool("1000 > 43;"));
        assertTrue(runBool("22 > 21;"));
        assertTrue(runBool("22 >= 21;"));
        assertTrue(runBool("22 >= 22;"));
    }
}
