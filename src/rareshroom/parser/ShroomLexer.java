package rareshroom.parser;

import java.util.ArrayList;
import java.util.List;

import static rareshroom.parser.Token.TokenType;

public class ShroomLexer {
    String sourceStr;
    int currentIdx;
    int symStartIdx;

    List<Token> tokens;

    ShroomLexer(String sourceInput) {
        this.sourceStr = sourceInput;
        this.tokens = new ArrayList<>();
    }

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

        addToken(TokenType.NUMBER, Double.parseDouble(sourceStr.substring(this.symStartIdx, this.currentIdx)));
    }

    private void addToken(Token.TokenType tokenType) {
        this.addToken(tokenType, null);
    }

    private void addToken(TokenType tokenType, Object literal) {
        String lexeme = this.sourceStr.substring(this.symStartIdx, this.currentIdx);
        this.tokens.add(new Token(tokenType, lexeme, literal));
    }

    public List<Token> getTokens() throws ParseError {
        while (this.currentIdx < sourceStr.length()) {
            this.symStartIdx = this.currentIdx;
            char c = advance();
            switch (c) {
                case '+' -> addToken(TokenType.PLUS);
                case '-' -> addToken(TokenType.MINUS);
                case '*' -> addToken(TokenType.STAR);
                case '/' -> addToken(TokenType.SLASH);
                case '(' -> addToken(TokenType.PAREN_OPEN);
                case ')' -> addToken(TokenType.PAREN_CLOSE);
                case '=' -> {
                    if (this.peek() == '=') {
                        addToken(TokenType.DOUBLE_EQUALS);
                        advance();
                    } else {
                        addToken(TokenType.EQUAL);
                    }
                }
                case '<' -> {
                    if (this.peek() == '=') {
                        addToken(TokenType.LESSER_EQUALS_THAN);
                        advance();
                    } else {
                        addToken(TokenType.LESSER_THAN);
                    }
                }
                case '>' -> {
                    if (this.peek() == '=') {
                        addToken(TokenType.GREATER_EQUALS_THAN);
                        advance();
                    } else {
                        addToken(TokenType.GREATER_THAN);
                    }
                }
                case '!' -> {
                    if (this.peek() == '=') {
                        addToken(TokenType.NOT_EQUALS);
                        advance();
                    } else {
                        addToken(TokenType.BANG);
                    }
                }
                case ' ', '\t' -> {}
                default -> {
                    if (isDigit(c)) {
                        number();
                    } else {
                        throw new ParseError("Unknown character : " + c);
                    }
                }
            }
        }
        this.symStartIdx = this.currentIdx;
        addToken(TokenType.EOF);
        return tokens;
    }
}

