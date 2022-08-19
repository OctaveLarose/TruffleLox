package lox.loop;

import lox.ExpressionTestsBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ForTests extends ExpressionTestsBase {
    @Test
    public void basicforTests() {
        assertEquals(runDouble("var i = 0; for (; i < 100; i = i + 1) {} i;"), 100.0, 0.01);
        assertEquals(runDouble("var a = 100; for (var i = 0; i < 100; i = i + 1) { a = a + 1; } a;"), 200.0, 0.01);
    }

    @Test
    @Ignore("breakTests() not ready, no break implemented")
    public void breakTests() {
        assertEquals(runDouble("var i = 0; for (;; i = i + 1) { if (i == 40) break; } i;"), 40, 0.01);
        assertEquals(runDouble("var i = 0; for (;;) { if (i == 40) { break; } else { i = i + 1; } } i;"), 40, 0.01);
    }

    @Test
    @Ignore("continueTests() not ready, no continue implemented")
    public void continueTests() {
        assertEquals(runDouble("var a = -13.13; for (int i = 0; i < 100; i = i + 1) { continue; a = a + 1; } a;"), -13.13, 0.01);
    }
}
