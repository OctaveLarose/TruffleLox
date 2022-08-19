package lox.loop;

import lox.ExpressionTestsBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoopTests extends ExpressionTestsBase {
    @Test
    public void whileTests() {
        assertEquals(runDouble("var a = 0; while (a < 10) { a = a + 1; } a;"), 10.0, 0.01);
        assertEquals(runDouble("var a = 0; while (false) { a = a + 1; } a;"), 0, 0.01);
    }

    @Test
    @Ignore("forTests() not ready yet")
    public void forTests() {
        assertEquals(runDouble("var a = 0; for (int i = 0; i < 100; i = i + 1) { a = a + 1; } a;"), 100.0, 0.01);
    }
}
