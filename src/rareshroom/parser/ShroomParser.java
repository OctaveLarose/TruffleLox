package rareshroom.parser;

public class ShroomParser {
    private String sourceStr;

    public void setInput(String inputStr) { this.sourceStr = inputStr;}

    public void parse() {
        ShroomLexer lexer = new ShroomLexer(this.sourceStr);
        try {
            System.out.println(lexer.getTokens());
        } catch (LexerException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
