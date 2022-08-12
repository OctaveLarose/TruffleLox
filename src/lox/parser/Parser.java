package lox.parser;

import lox.parser.error.ParseError;

import java.util.List;

public abstract class Parser {
    protected List<Token> tokens;

    protected int currentIdx = 0;

    protected boolean isAtEnd() {
        return peek().type == Token.TokenType.EOF;
    }

    protected Token peek() {
        return tokens.get(currentIdx);
    }

    protected boolean check(Token.TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    protected Token advance() {
        if (!isAtEnd()) currentIdx++;
        return previous();
    }
    protected Token previous() {
        return tokens.get(currentIdx - 1);
    }

    protected boolean match(Token.TokenType... types) {
        for (Token.TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    protected void error(String errorMsg) throws ParseError {
        throw new ParseError(errorMsg, this.tokens.get(this.currentIdx));
    }
}
