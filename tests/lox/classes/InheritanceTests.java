package lox.classes;

import lox.ExpressionTestsBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InheritanceTests extends ExpressionTestsBase {
    @Test
    public void basic() {
        String inheritStr = """
                class Soup {
                  souperMethod() {
                    return "superclass method";
                  }
                }

                class Chai < Soup {}
                Chai().souperMethod();
                """;

        assertEquals(runString(inheritStr), "superclass method");
    }

    @Test
    public void superCalls() {
        String superStr = """
                class Soup {
                  method() {
                    print "soup";
                  }
                }
                 
                 class Chai < Soup {
                   method() {
                     super.method();
                     print "chai";
                   }
                 }
                 
                 Chai().method();
                """;

        assertEquals("soup\nchai\n", runWithOutput(superStr));
    }

    @Ignore(ExpressionTestsBase.IMPLEMENTED_BUT_NO_EXEC)
    @Test
    public void invalidSuperAccess() {
        String superAccessStr = "print super;";

        assertEquals(runWithOutput(superAccessStr), "thiswillthrow");
    }
}
