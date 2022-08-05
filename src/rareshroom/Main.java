package rareshroom;

import rareshroom.parser.ShroomParser;

public class Main {
    static public void main(String[] args) {
        String sourceStr = "42 + 13 + 24";
        ShroomParser shroomParser = new ShroomParser(sourceStr);

        var parseOutput = shroomParser.parse();

        System.out.println("Output: " + parseOutput.executeGeneric());
    }
}