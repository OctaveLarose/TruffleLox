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
        return "[line " + failingToken.lineIdx + "] Error at '" + failingToken.lexeme +"': " + super.getMessage() + ".";
    }
}
