package rareshroom.parser;

import rareshroom.nodes.ExpressionNode;
import rareshroom.nodes.arithmetic.AddNodeFactory;
import rareshroom.nodes.arithmetic.DivNodeFactory;
import rareshroom.nodes.arithmetic.MultNodeFactory;
import rareshroom.nodes.arithmetic.SubNodeFactory;
import rareshroom.nodes.comparison.*;
import rareshroom.nodes.literals.NumberLiteralNode;

import java.util.List;

import static rareshroom.parser.Token.TokenType;

/*
    Grammar:

    expr ->     compare
    compare ->  term (("==" | "<" | "<=" | ">" | ">=") term)?
    term ->     factor (( "+" | "-" ) factor)*
    factor ->   unary (( "*" | "/" ) unary)*
    unary ->    ("!" | "-") unary | primary
    primary ->  NUMBER
                | "(" expr ")"
 */
public class ShroomParser {
    private final String sourceStr;

    private List<Token> tokens;

    private int currentIdx = 0;

    public ShroomParser(String inputStr) {
        this.sourceStr = inputStr;
    }

    public ExpressionNode parse() {
        ShroomLexer lexer = new ShroomLexer(this.sourceStr);
        try {
            this.tokens = lexer.getTokens();
        } catch (ParseError exception) {
            System.err.println(exception.getMessage());
        }

        try {
            return expression(); // TODO make root node
        } catch (ParseError parseError) {
            System.err.println(parseError.getMessage());
            return null;
        }
    }

    private ExpressionNode expression() throws ParseError {
        return compare();
    }

    private ExpressionNode compare() throws ParseError {
        ExpressionNode expr = term();

        if (match(TokenType.DOUBLE_EQUALS, TokenType.NOT_EQUALS,
                TokenType.GREATER_THAN, TokenType.GREATER_EQUALS_THAN,
                TokenType.LESSER_THAN, TokenType.LESSER_EQUALS_THAN)) {
            TokenType operatorType = previous().type;
            ExpressionNode arg = term();

            switch (operatorType) {
                case DOUBLE_EQUALS -> expr = EqualsNodeFactory.create(expr, arg);
                case GREATER_THAN -> expr = GreaterThanNodeFactory.create(expr, arg);
                case GREATER_EQUALS_THAN -> expr = GreaterOrEqualsToNodeFactory.create(expr, arg);
                case LESSER_THAN -> expr = LesserThanNodeFactory.create(expr, arg);
                case LESSER_EQUALS_THAN -> expr = LesserOrEqualsToNodeFactory.create(expr, arg);
                case NOT_EQUALS -> expr = NotEqualsNodeFactory.create(expr, arg);
            }
        }

        return expr;
    }

    private ExpressionNode term() throws ParseError {
        ExpressionNode expr = factor();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType operatorType = previous().type;
            ExpressionNode right = factor();

            switch (operatorType) {
                case PLUS -> expr = AddNodeFactory.create(expr, right);
                case MINUS -> expr = SubNodeFactory.create(expr, right);
            }
        }

        return expr;
    }

    private ExpressionNode factor() throws ParseError {
        ExpressionNode expr = unary();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            TokenType operatorType = previous().type;
            ExpressionNode right = unary();

            if (operatorType == TokenType.STAR)
                expr = MultNodeFactory.create(expr, right);
            else if (operatorType == TokenType.SLASH) {
                expr = DivNodeFactory.create(expr, right);
            }
        }

        return expr;
    }

    private ExpressionNode unary() throws ParseError {
        if (match(TokenType.BANG, TokenType.MINUS)) {
            TokenType operatorType = previous().type;
            switch (operatorType) {
                case BANG -> { return LogicalNotNodeFactory.create(unary()); }
                case MINUS -> {
                    ExpressionNode unary = unary();
                    if (unary instanceof NumberLiteralNode) {
                        double value = ((NumberLiteralNode) unary).executeDouble();
                        return NegateNodeFactory.create(new NumberLiteralNode(-value));
                    } else {
                        return NegateNodeFactory.create(unary);
                    }
                }
                default -> throw new ParseError("Should be unreachable");
            }
        } else {
            return primary();
        }
    }

    private ExpressionNode primary() throws ParseError {
        if (match(TokenType.NUMBER)) {
            return new NumberLiteralNode((Double) previous().literal);
        } else if (match(TokenType.PAREN_OPEN)) {
            ExpressionNode expr = expression();
            if (!match(TokenType.PAREN_CLOSE))
                throw new ParseError("Unclosed parentheses");
            return expr;
        } else {
            throw new ParseError("Invalid expression");
        }
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(currentIdx);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private void advance() {
        if (!isAtEnd()) currentIdx++;
//        return previous();
    }
    private Token previous() {
        return tokens.get(currentIdx - 1);
    }

    private boolean match(Token.TokenType... types) {
        for (Token.TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }
}
