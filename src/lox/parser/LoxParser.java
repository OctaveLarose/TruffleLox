package lox.parser;

import lox.nodes.*; // Bad habit but useful in this context of generated classes. TODO to be changed later though
import lox.nodes.arithmetic.*;
import lox.nodes.comparison.*;
import lox.nodes.conditionals.IfElseNode;
import lox.nodes.conditionals.IfNode;
import lox.nodes.functions.*;
import lox.nodes.literals.*;
import lox.nodes.statements.*;
import lox.nodes.variables.*;
import lox.parser.error.ParseError;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static lox.parser.Token.TokenType;

/*
    Grammar:

    program ->      statement* EOF
    declaration ->  funStmt
                    | statement

    funStmt ->      "fun" "{" parameters? "}" block
    block ->        "{" declaration* "}"
    parameters ->   IDENTIFIER ("," IDENTIFIER)*

    statement ->    exprStmt
                    | ifStmt
                    | printStmt
                    | returnStmt
                    | block
    ifStmt ->       "if" "(" expression ")" statement ( "else" statement )? ;
    exprStmt ->     expr ";"
    returnStmt     → "return" expression? ";" ;

    expr ->         assignment
    assignment ->   IDENTIFIER "=" assignment
                    | orExpr
    orExpr ->       andExpr ( "or" andExpr )* ;
    andExpr ->      equality ( "and" equality )* ;
    equality ->     compare ( ( "!=" | "==" ) compare )* ;
    compare ->      term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
    term ->         factor (( "-" | "+" ) factor)*
    factor ->       unary (( "/" | "*" ) unary)*
    unary ->        ("!" | "-") unary
                    | primary
    primary ->      "true" | "false" | "nil"
                    | NUMBER | STRING | IDENTIFIER
                    | "(" expr ")"
 */
public class LoxParser extends Parser {
    private final String sourceStr;

    private HashMap<String, Integer> functionParameters;

    @SuppressWarnings("unused") // Used for debugging
    public LoxParser(String inputStr) {
        this.sourceStr = inputStr;
    }

    public LoxParser(Reader inputReader) {
        String readerStr;
        StringBuilder targetString = new StringBuilder();
        int intValueOfChar;

        try {
            while ((intValueOfChar = inputReader.read()) != -1) // TODO remove this temporary ugly fix
                targetString.append((char) intValueOfChar);
            readerStr = targetString.toString();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
            readerStr = "";
        }
        this.sourceStr = readerStr;
    }

    public ExpressionNode parse() throws ParseError {
        Lexer lexer = new Lexer(this.sourceStr);
        this.tokens = lexer.getTokens();
        return program();
    }

    private ExpressionNode program() throws ParseError {
        ArrayList<ExpressionNode> expressions = new ArrayList<>();
        while (!isAtEnd()) {
            var declaration = declaration();
            expressions.add(declaration);
        }
        return new SequenceNode(expressions);
    }

    private ExpressionNode declaration() throws ParseError {
        if (match(TokenType.FUN)) {
            return function();
        }
        return statement();
    }

    private ExpressionNode function() throws ParseError {
        Token idToken = this.peek();

        if (!match(TokenType.IDENTIFIER))
            error("No identifier specified for function declaration");

        if (!match(TokenType.PAREN_OPEN))
            error("Expected a '(' before function arguments");

        List<String> parameters = parameters();

        this.functionParameters = new HashMap<>();
        for (int i = 0; i < parameters.size(); i++)
            this.functionParameters.put(parameters.get(i), i);

        if (!match(TokenType.CURLY_BRACKET_OPEN))
            error("Expected a '{' to specify a function declaration");

        ExpressionNode block = block();

        this.functionParameters.clear();
        this.functionParameters = null;

        return new FunctionDeclarationNode((String)idToken.literal, parameters, block);
    }

    private ExpressionNode statement() throws ParseError {
        if (match(TokenType.CURLY_BRACKET_OPEN))
            return block();
        else if (match(TokenType.IF))
            return ifStmt();
        else if (match(TokenType.PRINT))
            return printStmt();
        else if (match(TokenType.RETURN))
            return returnStmt();
        else
            return exprStatement();
    }

    private ExpressionNode ifStmt() throws ParseError {
        if (!match(TokenType.PAREN_OPEN))
            error("Expected a '(' after keyword \"if\"");
        ExpressionNode conditionalExpression = expression();

        if (!match(TokenType.PAREN_CLOSE))
            error("Expected a ')' after \"if\" conditional expression");

        ExpressionNode consequent = statement();

        if (match(TokenType.ELSE)) {
            ExpressionNode alternative = statement();
            return new IfElseNode(conditionalExpression, consequent, alternative);
        }

        return new IfNode(conditionalExpression, consequent);
    }

    private ExpressionNode printStmt() throws ParseError {
        ExpressionNode expr = exprStatement();
        return PrintStatementFactory.create(expr);
    }

    private ExpressionNode block() throws ParseError {
        List<ExpressionNode> declarations = new ArrayList<>();

        while (!isAtEnd() && !match(TokenType.CURLY_BRACKET_CLOSE)) {
            var declaration = declaration();
            declarations.add(declaration);
        }

        return new BlockNode(declarations);
    }

    private ExpressionNode exprStatement() throws ParseError {
        ExpressionNode expression = expression();

        if (!match(TokenType.SEMICOLON))
            error("Unterminated statement");

        return expression;
    }

    private ExpressionNode expression() throws ParseError {
        return assignment();
    }

    private ExpressionNode assignment() throws ParseError {
        ExpressionNode assignee = orExpr();

        if (match(TokenType.EQUALS)) {
            ExpressionNode assignment = assignment();

            if (assignee instanceof GlobalVariableNode) {
                String varName = (((GlobalVariableNode)assignee).getLocalVariable().getName());
                return GlobalVariableNodeFactory.VariableWriteNodeGen.create(varName, assignment);
            }

            if (assignee instanceof ArgumentNode) {
                int argIdx = ((ArgumentNode)assignee).getIdx();
                return ArgumentNodeFactory.ArgumentWriteNodeGen.create(argIdx, assignment);
            }

            error("Invalid assignment target");
        }
        return assignee;
    }

    private ExpressionNode orExpr() throws ParseError {
        ExpressionNode expr = andExpr();

        while (match(TokenType.OR)) {
            expr = LogicalOrNodeFactory.create(expr, andExpr());
        }

        return expr;
    }

    private ExpressionNode andExpr() throws ParseError {
        ExpressionNode expr = equality();

        while (match(TokenType.AND)) {
            expr = LogicalAndNodeFactory.create(expr, equality());
        }

        return expr;
    }

    private ExpressionNode equality() throws ParseError {
        ExpressionNode expr = compare();

        if (match(TokenType.DOUBLE_EQUALS, TokenType.NOT_EQUALS)) {
            TokenType operatorType = previous().type;
            ExpressionNode arg = term();

            switch (operatorType) {
                case NOT_EQUALS -> expr = NotEqualsNodeFactory.create(expr, arg);
                case DOUBLE_EQUALS -> expr = EqualsNodeFactory.create(expr, arg);
            }
        }

        return expr;
    }

    private ExpressionNode compare() throws ParseError {
        ExpressionNode expr = term();

        if (match(TokenType.GREATER_THAN, TokenType.GREATER_EQUALS_THAN,
                TokenType.LESSER_THAN, TokenType.LESSER_EQUALS_THAN)) {
            TokenType operatorType = previous().type;
            ExpressionNode arg = term();

            switch (operatorType) {
                case GREATER_THAN -> expr = GreaterThanNodeFactory.create(expr, arg);
                case GREATER_EQUALS_THAN -> expr = GreaterOrEqualsToNodeFactory.create(expr, arg);
                case LESSER_THAN -> expr = LesserThanNodeFactory.create(expr, arg);
                case LESSER_EQUALS_THAN -> expr = LesserOrEqualsToNodeFactory.create(expr, arg);
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
                case MINUS -> expr = SubNodeFactory.create(expr, right);
                case PLUS -> expr = AddNodeFactory.create(expr, right);
            }
        }

        return expr;
    }

    private ExpressionNode factor() throws ParseError {
        ExpressionNode expr = unary();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            TokenType operatorType = previous().type;
            ExpressionNode right = unary();

            switch (operatorType) {
                case SLASH -> expr = DivNodeFactory.create(expr, right);
                case STAR -> expr = MultNodeFactory.create(expr, right);
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
                        double value = ((NumberLiteralNode) unary).executeDouble(null);
                        return new NumberLiteralNode(-value);
                    } else {
                        return NegateNodeFactory.create(unary);
                    }
                }
                default -> error("Should be unreachable");
            }
        }
        return primary();
    }

    private ExpressionNode primary() throws ParseError {
        Token currentToken = advance();

        switch (currentToken.type) {
            case TRUE -> { return new TrueLiteralNode(); }
            case FALSE -> { return new FalseLiteralNode(); }
            case NIL -> { return new NilLiteralNode(); }
            case NUMBER -> { return new NumberLiteralNode((Double) currentToken.literal); }
            case STRING -> { return new StringLiteralNode((String) currentToken.literal); }
            case PAREN_OPEN -> {
                ExpressionNode expr = expression();
                if (!match(TokenType.PAREN_CLOSE))
                    error("Unclosed parentheses");
                return expr;
            }
            case IDENTIFIER -> {
                if (match(TokenType.PAREN_OPEN)) { // TODO: should instead be handled in a call() function above
                    List<ExpressionNode> arguments = arguments();
                    return new FunctionCallNode(new FunctionLookupNode((String)currentToken.literal), arguments); // Shouldn't always have to look it up, but it's a start
                }
                if (this.functionParameters != null) { // i.e. whether we are currently parsing a function
                    Integer argIdx = this.functionParameters.get((String) currentToken.literal);

                    if (argIdx != null)
                        return ArgumentNodeFactory.ArgumentReadNodeGen.create(argIdx);
                }
                return GlobalVariableNodeFactory.VariableReadNodeGen.create(((String) currentToken.literal)); }
            default -> error("Invalid expression: primary token of type " + currentToken.type);
        }

        return null; // Unreachable
    }

    private List<ExpressionNode> arguments() throws ParseError {
        ArrayList<ExpressionNode> arguments = new ArrayList<>();
        if (match(TokenType.PAREN_CLOSE))
            return arguments;

        while (!isAtEnd()) {
            ExpressionNode expr = expression();
            arguments.add(expr);
            if (!match(TokenType.COMMA))
                break;
        }

        if (!match(TokenType.PAREN_CLOSE))
            error("Unterminated arguments declaration");

        return arguments;
    }

    private List<String> parameters() throws ParseError {
        ArrayList<String> parameters = new ArrayList<>();
        if (match(TokenType.PAREN_CLOSE))
            return parameters;

        while (!isAtEnd()) {
            Token idToken = advance();
            if (idToken.type != TokenType.IDENTIFIER)
                error("Expected an identifier in parameters list, instead got " + idToken);
            parameters.add((String) idToken.literal);

            if (!match(TokenType.COMMA))
                break;
        }

        if (!match(TokenType.PAREN_CLOSE))
            error("Unterminated parameters declaration");

        return parameters;
    }

    private ExpressionNode returnStmt() throws ParseError {
        if (match(TokenType.SEMICOLON))
            return new ReturnStmt(null);

        ExpressionNode expr = expression();

        if (!match(TokenType.SEMICOLON))
            error("Unterminated return statement");

        return new ReturnStmt(expr);
    }
}
