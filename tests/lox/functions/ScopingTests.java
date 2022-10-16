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

    @Test
    public void globalVsBlock() {
        String testStr = """
                var a = "global";
                {
                    fun showA() {
                       print a;
                    }
        
                    showA();
                    var a = "block";
                    showA();
                }
                """;

        assertEquals("global\nblock\n", runWithOutput(testStr));
    }
}
