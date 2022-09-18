package lox.loop;

import lox.ExpressionTestsBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ForTests extends ExpressionTestsBase {
    @Test
    public void basicforTests() {
        assertEquals(100, runInt("var i = 0; for (; i < 100; i = i + 1) {} i;"));
        assertEquals(200, runInt("var a = 100; for (var i = 0; i < 100; i = i + 1) { a = a + 1; } a;"),0.01);
    }

    @Test
    @Ignore("breakTests() not ready, no break implemented")
    public void breakTests() {
        assertEquals(40, runInt("var i = 0; for (;; i = i + 1) { if (i == 40) break; } i;"));
        assertEquals(40, runInt("var i = 0; for (;;) { if (i == 40) { break; } else { i = i + 1; } } i;"));
    }

    @Test
    @Ignore("continueTests() not ready, no continue implemented")
    public void continueTests() {
        assertEquals(-13.13, runDouble("var a = -13.13; for (int i = 0; i < 100; i = i + 1) { continue; a = a + 1; } a;"), 0.01);
    }
}
