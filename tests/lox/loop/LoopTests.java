package lox.loop;

import lox.ExpressionTestsBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoopTests extends ExpressionTestsBase {
    @Test
    public void whileTests() {
        assertEquals(runDouble("var a = 0; while (a < 10) { a = a + 1; } a;"), 10.0, 0.01);
        assertEquals(runDouble("var a = 0; while (false) { a = a + 1; } a;"), 0, 0.01);
    }
}
