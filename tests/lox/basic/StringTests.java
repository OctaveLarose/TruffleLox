package lox.basic;

import lox.ExpressionTestsBase;
import org.graalvm.polyglot.PolyglotException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StringTests extends ExpressionTestsBase {
    @Test
    public void equality() {
        assertTrue(runBool("\"hello world\" == \"hello world\";"));
        assertTrue(runBool("\"AbcD\" != \"abCd\";"));
    }

//    @Test
//    public void invalid() {
//        assertThrows(PolyglotException.class, () -> {runBool("\"hello world\" >= \"hello world\";");});
//    }
}
