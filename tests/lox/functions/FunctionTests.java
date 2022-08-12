package lox.functions;

import lox.ExpressionTestsBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionTests extends ExpressionTestsBase {
    @Test
    public void functions() {
        assertEquals(runDouble("fun addThree(a) { return a + 3; } addThree(-3);"), 0.0, 0.001);
        assertEquals(runDouble("fun add(a, b) { return a + b; } add(1000, 999);"), 1999.0, 0.001);
        assertEquals(runDouble("fun returnThreeToTwoArgs(a, b) { return a + b + 3; } returnThreeToTwoArgs(3, 4) + 1;"), 11.0, 0.001);
    }

    @Ignore("Not implemented, TODO")
    @Test
    public void noSharedState() {
        assertEquals(runDouble("fun addA(input) { a = 24; return a + input; } a = 42; add(a);"), 66.0, 0.001);
    }
}
