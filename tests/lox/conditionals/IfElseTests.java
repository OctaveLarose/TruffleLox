package lox.conditionals;

import lox.ExpressionTestsBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IfElseTests extends ExpressionTestsBase {
    @Test
    public void ifTests() {
        assertEquals(runDouble("var a = 42; if (a > 12) { a + 2; }"), 44.0, 0.01);
        assertEquals(runDouble("if (true) { -24; }"), -24.0, 0.01);
        assertTrue(returnsNull("if (false) { 0; }"));
    }

    @Test
    public void ifElseTests() {
        assertEquals(runDouble("var a = 42; if (a == 12) { a + 2; } else { a = 0; }"), 0.0, 0.01);
        assertEquals(runDouble("var a = 42; if (-1 == -1) { if (a > 5) a = a - 24; } else { a = 0; }"), 18, 0.01);
        assertTrue(returnsNull("if (false == true) { 42; } else { nil; }"));
    }

    @Test
    public void ifElseElseEtc() {
        assertEquals(runDouble("var a = 42; if (a == 12) { a + 2; } else if (a == 13) { a = 13; } else { a = 0; }"), 0.0, 0.01);
    }
}
