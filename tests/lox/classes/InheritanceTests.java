package lox.classes;

import lox.ExpressionTestsBase;
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
}
