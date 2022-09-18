package lox.functions;

import lox.ExpressionTestsBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionTests extends ExpressionTestsBase {
    @Test
    public void basic() {
        assertEquals(runDouble("fun addThree(a) { return a + 3; } addThree(-3);"), 0.0, 0.001);
        assertEquals(runDouble("fun add(a, b) { return a + b; } add(1000, 999);"), 1999.0, 0.001);
        assertEquals(runDouble("fun returnThreeToTwoArgs(a, b) { return a + b + 3; } returnThreeToTwoArgs(3, 4) + 1;"), 11.0, 0.001);
    }

    @Test
    public void cascadingReturns() {
        String cascadingReturns =   "fun nested(input) { return input + 2; } " +
                                    "fun callNested(input) { var a = input * 2; return nested(input) + a; } " +
                                    "callNested(10);";

        assertEquals(runDouble(cascadingReturns), 32.0, 0.001);
    }

    @Test
    public void printFunction() {
        assertEquals(runWithOutput("fun abc() { return 3; } print abc;"), "<fn abc>\n");
    }

    @Test
    public void editInputArgs() {
        assertEquals(runDouble("fun func(a) { a = 1000; return a; } func(1);"), 1000.0, 0.001);
        assertEquals(runDouble("fun func(a, b, c) { c = 1; c = b = 2; a = b = c; return a + b + c; } func(1, 1, 1);"), 6, 0.001);
    }

    @Test
    public void noSharedState() {
        assertEquals(runDouble("fun addA(input) { var a = 24; return a + input; } var a = 42; addA(a);"), 66.0, 0.001);
    }

    @Ignore(IMPLEMENTED_BUT_NO_EXEC)
    @Test
    public void noShadowing() {
        // should throw!
        assertEquals(runDouble("fun addA(input) { var input = 34; return input; } var a = 42; add(a);"), 66.0, 0.001);
    }

    @Ignore(IMPLEMENTED_BUT_NO_EXEC)
    @Test
    public void noCallByString() {
        // should throw!
        assertEquals(runDouble("fun abc() { print 3; } \"abc\"();"), 66.0, 0.001);
    }
}
