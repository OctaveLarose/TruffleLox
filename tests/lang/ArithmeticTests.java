package lang;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ArithmeticTests extends ExpressionTests {

    public ArithmeticTests() {}

    @Test
    public void plus() {
        assertTrue(runBool("13 + 42 == 55;"));
        assertTrue(runBool("1 + 55 + 3812 == 3868;"));
    }

    @Test
    public void minus() {
        assertTrue(runBool("4617 - 134 == 4483;"));
        assertTrue(runBool("4617 - 134 + 18291 == 22774;"));
    }

    @Test
    public void mult() {
        assertTrue(runBool("3 * 4 == 12;"));
        assertTrue(runBool("3 * 18 + 13 == 67;"));
        assertTrue(runBool("12 + 65 * 9  == 3 * 199;"));
    }

    @Test
    public void div() {
        assertTrue(runBool("18 / 3 == 6;"));
        assertTrue(runBool("1783 / 3 == 30311 / 51;"));
        assertTrue(runBool("2377 / 4 == 594.25;"));
        assertTrue(runBool("163 * 5 + 100 / 4 - 12 * 2 + 145 == 961;"));
        assertTrue(runBool("163 * (5 + 100 / 4) - 12 * (2 + 145) == 3126;"));
    }

    @Test
    public void negate() {
        assertTrue(runBool("-(22 * 3) == ---(22 * 3);"));
        assertTrue(runBool("--((22 * 3)) == 22 * 3;"));
        assertTrue(runBool("-32.8 == -8.2 * 4;"));
    }

    @Test
    public void greater() {
        assertTrue(runBool("1 < 2;"));
        assertTrue(runBool("42 <= 43;"));
        assertTrue(runBool("43 <= 43;"));
    }

    @Test
    public void lesser() {
        assertTrue(runBool("1000 > 43;"));
        assertTrue(runBool("22 > 21;"));
        assertTrue(runBool("22 >= 21;"));
        assertTrue(runBool("22 >= 22;"));
    }
}