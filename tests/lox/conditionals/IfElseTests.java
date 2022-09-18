package lox.conditionals;

import lox.ExpressionTestsBase;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IfElseTests extends ExpressionTestsBase {
    @Test
    public void ifTests() {
        assertEquals(44, runInt("var a = 42; if (a > 12) { a + 2; }"));
        assertEquals(-24, runInt("if (true) { -24; }"));
        assertTrue(returnsNull("if (false) { 0; }"));
    }

    @Test
    public void ifElseTests() {
        assertEquals(2.42, runDouble("var a = 42; if (a == 12) { a + 2; } else { a = 2.42; }"), 0.01);
        assertEquals(18, runInt("var a = 42; if (-1 == -1) { if (a > 5) a = a - 24; } else { a = 0; }"));
        assertTrue(returnsNull("if (false == true) { 42; } else { nil; }"));
    }

    @Test
    public void ifElseElseEtc() {
        assertEquals(0, runInt("var a = 42; if (a == 12) { a + 2; } else if (a == 13) { a = 13; } else { a = 0; }"));
    }

    @Test
    public void ifTruthyFalsey() {
        assertEquals(42, runInt("if (42222) { 42; }"));
        assertEquals(24, runInt("if (nil) { 42; } else { 24; }"));
    }
}
