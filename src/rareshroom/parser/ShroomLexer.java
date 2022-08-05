package rareshroom.parser;

import java.util.ArrayList;
import java.util.List;

public class ShroomLexer {
    String sourceStr;
    int currentIdx;
    int symStartIdx;

    List<Token> tokens;

    ShroomLexer(String sourceInput) {
        this.sourceStr = sourceInput;
        this.tokens = new ArrayList<>();
    }

    enum TokenType {
        MINUS, PLUS, SLASH, STAR,
        EQUAL,
        NUMBER,
        SEMICOLON,
        EOF
    }

    static class Token {
        final TokenType type;
        final String lexeme;
        final Object literal;

        Token(TokenType type, String lexeme, Object literal) {
            this.type = type;
            this.lexeme = lexeme;
            this.literal = literal;
        }

        public String toString() {
            return type + " "
                    + (literal != null ? literal : "")
                    + " \"" + lexeme + "\"";
        }
    }

    private static final String[] literalNames = new String[] {null, "+", "-", "*", "/", "=", ";"};

    private char peek() {
        if (this.currentIdx >= this.sourceStr.length()) return 0;
        return this.sourceStr.charAt(this.currentIdx);
    }

    private char peekNext() {
        if (this.currentIdx + 1 >= this.sourceStr.length())
            return 0;
        return this.sourceStr.charAt(this.currentIdx + 1);
    }

    private char advance() {
        return this.sourceStr.charAt(this.currentIdx++);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) advance();
        }

        addToken(TokenType.NUMBER, Long.parseLong(sourceStr.substring(this.symStartIdx, this.currentIdx)));
    }

    private void addToken(TokenType tokenType) {
        this.addToken(tokenType, null);
    }

    private void addToken(TokenType tokenType, Object literal) {
        String lexeme = this.sourceStr.substring(this.symStartIdx, this.currentIdx);
        this.tokens.add(new Token(tokenType, lexeme, literal));
    }

    public List<Token> getTokens() throws LexerException {
        while (this.currentIdx < sourceStr.length()) {
            this.symStartIdx = this.currentIdx;
            char c = advance();
            switch (c) {
                case '+':
                    addToken(TokenType.PLUS);
                    break;
                case '-':
                    addToken(TokenType.MINUS);
                    break;
                case '*':
                    addToken(TokenType.STAR);
                    break;
                case '/':
                    addToken(TokenType.SLASH);
                    break;
                case ' ':
                case '\t':
                    break;
                default:
                    if (isDigit(c)) {
                        number();
                    } else {
                        throw new LexerException("Unknown character : " + c);
                    }
            }
        }
        return tokens;
    }
}

