package rareshroom;

import rareshroom.parser.ShroomParser;

// Should be proper tests later on
public class LanguageTests {
    static private Object run(String sourceStr) {
        return new ShroomParser(sourceStr).parse().executeGeneric();
    }

    static public void runTests() {
        arithmeticTests();
        logicTests();
        invalidParsingTests();
    }
    static public void arithmeticTests() {
        System.out.println("Arithmetic tests:");

        // Addition, substraction
        assertExpr("13 + 42 == 55");
        assertExpr("1 + 55 + 3812 == 3868");
        assertExpr("4617 - 134 == 4483");
        assertExpr("4617 - 134 + 18291 == 22774");

        // Multiplication
        assertExpr("3 * 4 == 12");
        assertExpr("3 * 18 + 13 == 67");
        assertExpr("12 + 65 * 9  == 3 * 199");

        // Division
        assertExpr("18 / 3 == 6");
        assertExpr("1783 / 3 == 30311 / 51");
        assertExpr("2377 / 4 == 594.25");
        assertExpr("163 * 5 + 100 / 4 - 12 * 2 + 145 == 961");
        assertExpr("163 * (5 + 100 / 4) - 12 * (2 + 145) == 3126");

        // Not equals
        assertExpr("1 != 2");
        assertExpr("163 * 5 + 100 / 4 - 12 * 2 + 145 != 10000000000");

        // Greater/lesser
        assertExpr("1 < 2");
        assertExpr("42 <= 43");
        assertExpr("43 <= 43");
        assertExpr("1000 > 43");
        assertExpr("22 > 21");
        assertExpr("22 >= 21");
        assertExpr("22 >= 22");

        // Negate
        assertExpr("-(22 * 3) == ---(22 * 3)");
        assertExpr("--((22 * 3)) == 22 * 3");
        assertExpr("-32.8 == -8.2 * 4");
    }

    static public void logicTests() {
        System.out.println("Logic tests:");

        // And
        assertExpr("!((22 >= 10000) and (100 != 1))");
        assertExpr("!(22 >= 10000 and 100 != 1)");
        assertExpr("true and true");
        assertExpr("!(true and false)");
        assertExpr("!(false and false)");
        assertExpr("!false and (1 > 0 and (3 * 4 == 12))");

        // Or
        assertExpr("true or false");
        assertExpr("!(false or false)");

        // Not
        assertExpr("!(22 >= 10000) == (10 > 5)");
        assertExpr("!false");
        assertExpr("!!true");

        // Truthiness/falsiness
        assertExpr("42 or 1");
        assertExpr("false == nil");
        assertExpr("!(42 and nil)");
        assertExpr("!(42.24 and nil)");
    }

    static public void invalidParsingTests() {
        // Invalid syntax
        // Invalid semantics
        ; // TODO probably warrants moving to an actual test library
    }

    static private void assertExpr(String input) {
        System.out.print(input);
        Object output = run(input);
        if (output instanceof Boolean && (boolean) output) {
            System.out.print("\u001B[32m - OK\u001B[0m\n");
        } else {
            System.out.print("\u001B[31m - ERROR\u001B[0m\n");
        }
    }
}
