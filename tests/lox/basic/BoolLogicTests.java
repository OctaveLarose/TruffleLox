package lox.basic;

import lox.ExpressionTestsBase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BoolLogicTests extends ExpressionTestsBase {
    @Test
    public void and() {
        assertTrue(runBool("!((22 >= 10000) and (100 != 1));"));
        assertTrue(runBool("!(22 >= 10000 and 100 != 1);"));
        assertTrue(runBool("true and true;"));
        assertTrue(runBool("!(true and false);"));
        assertTrue(runBool("!(false and false);"));
        assertTrue(runBool("!false and (1 > 0 and (3 * 4 == 12));"));
    }

    @Test
    public void or() {
        assertTrue(runBool("true or false;"));
        assertTrue(runBool("!(false or false);"));
    }

    @Test
    public void not() {
        assertTrue(runBool("!(22 >= 10000) == (10 > 5);"));
        assertTrue(runBool("!false;"));
        assertTrue(runBool("!!true;"));
    }

    @Test
    public void truthyFalseyRules() {
        assertTrue(runBool("42 or 1;"));
        assertTrue(runBool("1 and 0;"));
        assertTrue(runBool("1 and -1;"));
        assertTrue(runBool("1000 and -42;"));
        assertTrue(runBool("false == nil;"));
        assertTrue(runBool("!(42 and nil);"));
        assertTrue(runBool("!(42.24 and nil);"));
    }
}
