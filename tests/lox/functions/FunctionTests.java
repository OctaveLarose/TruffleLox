package lox.functions;

import lox.ExpressionTestsBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionTests extends ExpressionTestsBase {
    @Test
    public void basic() {
        assertEquals(0, runInt("fun addThree(a) { return a + 3; } addThree(-3);"), 0.001);
        assertEquals(1999, runInt("fun add(a, b) { return a + b; } add(1000, 999);"), 0.001);
        assertEquals(11, runInt("fun returnThreeToTwoArgs(a, b) { return a + b + 3; } returnThreeToTwoArgs(3, 4) + 1;"),0.001);
    }

    @Test
    public void cascadingReturns() {
        String cascadingReturns = "fun nested(input) { return input + 2; } " +
                                  "fun callNested(input) { var a = input * 2; return nested(input) + a; } " +
                                  "callNested(10);";

        assertEquals(32, runInt(cascadingReturns), 0.001);
    }

    @Test
    public void printFunction() {
        assertEquals(runWithOutput("fun abc() { return 3; } print abc;"), "<fn abc>\n");
    }

    @Test
    public void editInputArgs() {
        assertEquals(1000, runInt("fun func(a) { a = 1000; return a; } func(1);"), 0.001);
        assertEquals(6, runInt("fun func(a, b, c) { c = 1; c = b = 2; a = b = c; return a + b + c; } func(1, 1, 1);"), 0.001);
    }

    @Test
    public void noSharedState() {
        assertEquals(66, runInt("fun addA(input) { var a = 24; return a + input; } var a = 42; addA(a);"), 0.001);
    }

    @Ignore(IMPLEMENTED_BUT_NO_EXCEPT_HANDLING)
    @Test
    public void noShadowing() {
        // should throw!
        assertEquals(66, runInt("fun addA(input) { var input = 34; return input; } var a = 42; add(a);"), 0.001);
    }

    @Ignore(IMPLEMENTED_BUT_NO_EXCEPT_HANDLING)
    @Test
    public void noCallByString() {
        // should throw!
        assertEquals(66, runInt("fun abc() { print 3; } \"abc\"();"), 0.001);
    }
}
