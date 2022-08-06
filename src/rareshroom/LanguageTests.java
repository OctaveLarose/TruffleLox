package rareshroom;

import rareshroom.parser.ShroomParser;

// Should be proper tests later on
public class LanguageTests {
    static private Object run(String sourceStr) {
        return new ShroomParser(sourceStr).parse().executeGeneric();
    }

    static public void runTests() {
        arithmeticAsserts();
    }
    static public void arithmeticAsserts() {
        // Addition, substraction
        arithmeticAssert("13 + 42 == 55");
        arithmeticAssert("1 + 55 + 3812 == 3868");
        arithmeticAssert("4617 - 134 == 4483");
        arithmeticAssert("4617 - 134 + 18291 == 22774");

        // Multiplication
        arithmeticAssert("3 * 4 == 12");
        arithmeticAssert("3 * 18 + 13 == 67");
        arithmeticAssert("12 + 65 * 9  == 3 * 199");

        // Division
        arithmeticAssert("18 / 3 == 6");
        arithmeticAssert("1783 / 3 == 30311 / 51");
        arithmeticAssert("2377 / 4 == 594.25");
        arithmeticAssert("163 * 5 + 100 / 4 - 12 * 2 + 145 == 961");
        arithmeticAssert("163 * (5 + 100 / 4) - 12 * (2 + 145) == 3126");

        // Not equals
        arithmeticAssert("1 != 2");
        arithmeticAssert("163 * 5 + 100 / 4 - 12 * 2 + 145 != 10000000000");

        // Greater/lesser
        arithmeticAssert("1 < 2");
        arithmeticAssert("42 <= 43");
        arithmeticAssert("43 <= 43");
        arithmeticAssert("1000 > 43");
        arithmeticAssert("22 > 21");
        arithmeticAssert("22 >= 21");
        arithmeticAssert("22 >= 22");
    }

    static private void arithmeticAssert(String input) {
        System.out.print(input);
        Object output = run(input);
        if (output instanceof Boolean && (boolean) output) {
            System.out.print(" - OK\n");
        } else {
            System.out.print(" - ERROR\n");
        }
    }
}
