package rareshroom;

import rareshroom.parser.ShroomParser;

public class EntryPoint {
    static public void main(String[] args) {
        arithmeticAsserts();
    }

    static private Object run(String sourceStr) {
        return new ShroomParser(sourceStr).parse().executeGeneric();
    }

    static private void arithmeticAsserts() {
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
        arithmeticAssert("1783 / 3 == 594"); // You should not be valid though. Needs to handle doubles
        arithmeticAssert("163 * 5 + 100 / 4 - 12 * 2 + 145 == 961");
        arithmeticAssert("163 * (5 + 100 / 4) - 12 * (2 + 145) == 3126");

        // Not equals
        arithmeticAssert("1 != 2");
        arithmeticAssert("163 * 5 + 100 / 4 - 12 * 2 + 145 != 10000000000");
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