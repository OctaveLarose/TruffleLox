package lox.basic;

import lox.ExpressionTestsBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
