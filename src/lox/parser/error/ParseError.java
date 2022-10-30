package lox.parser.error;

import lox.parser.Token;

public class ParseError extends Exception {
    private final Token failingToken;

    public ParseError(String message, Token failingToken) {
        super(message);
        this.failingToken = failingToken;
    }

    @Override
    public String getMessage() {
        if (failingToken.type == Token.TokenType.EOF)
            return "[line " + failingToken.lineIdx + "] Error at end: " + super.getMessage() + ".";

        return "[line " + failingToken.lineIdx + "] Error at '" + failingToken.lexeme +"': " + super.getMessage() + ".";
    }
}
