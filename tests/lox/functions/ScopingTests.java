package lox.functions;

import lox.ExpressionTestsBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScopingTests extends ExpressionTestsBase {
    @Test
    public void innerOuter() {
        String testStr = """
                var a = "outer";
                                
                {
                  var b = "inner";
                  print b;
                }
                                
                print a;
                """;
        assertEquals("inner\nouter\n", runWithOutput(testStr));
    }
}
