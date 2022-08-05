package rareshroom;

import rareshroom.parser.ShroomParser;

public class Main {
    static public void main(String[] args) {
        ShroomParser shroomParser = new ShroomParser();

        shroomParser.setInput("42 + 13");
        shroomParser.parse();
    }
}