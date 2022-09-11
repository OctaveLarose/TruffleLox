package lox.parser;

import com.oracle.truffle.api.source.Source;

import lox.nodes.*; // Bad habit but useful in this context of generated classes. To be changed later though
import lox.nodes.calls.*;
import lox.nodes.arithmetic.*;
import lox.nodes.classes.*;
import lox.nodes.comparison.*;
import lox.nodes.conditionals.*;
import lox.nodes.functions.*;
import lox.nodes.literals.*;
import lox.nodes.loop.*;
import lox.nodes.statements.*;
import lox.nodes.variables.*;
import lox.parser.error.ParseError;

import java.util.ArrayList;
import java.util.List;

import static lox.parser.Token.TokenType;

public class LoxParser extends Parser {
    private final String ROOT_FUNCTION_NAME = "_main";
    private final String sourceStr;

    private FunctionContext currentScope;

    @SuppressWarnings("unused") // Used for debugging
    public LoxParser(String inputStr) {
        this.sourceStr = inputStr;
    }

    // Ideally we would fully leverage the Source object to get better debugging info, but this is good enough for a toy language
    public LoxParser(Source source) {
        this.sourceStr = (String) source.getCharacters();
    }

    public LoxRootNode parse() throws ParseError {
        Lexer lexer = new Lexer(this.sourceStr);
        this.tokens = lexer.getTokens();

        this.currentScope = new FunctionContext(ROOT_FUNCTION_NAME);

        ExpressionNode program = program();

        return new LoxRootNode(program, this.currentScope.getFrameDescriptor());
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
        if (match(TokenType.CLASS)) {
            return classDecl();
        } else if (match(TokenType.FUN)) {
            return funDecl();
        } else if (match(TokenType.VAR)) {
            return varDecl();
        }
        return statement();
    }

    private ClassDeclarationNode classDecl() throws ParseError {
//        classDecl ->    "class" IDENTIFIER ( "<" IDENTIFIER )? "{" function* "}" ;
        Token identifierToken = peek();

        if (!match(TokenType.IDENTIFIER))
            error("Expect an identifier after class declaration");

        String className = (String) identifierToken.literal;

        String extendsClass = null;
        if (match(TokenType.LESSER_THAN)) {
            Token superClassToken = peek();
            if (!match(TokenType.IDENTIFIER))
                error("Expect identifier after superclass declaration");
            extendsClass = (String) superClassToken.literal;
        }

        if (!match(TokenType.CURLY_BRACKET_OPEN))
            error("Expect a { to define the class body");


        List<FunctionDeclarationNode> methods = new ArrayList<>();
        while (!isAtEnd() && !match(TokenType.CURLY_BRACKET_CLOSE)) {
            FunctionDeclarationNode funDecl = funDecl();
            funDecl.setIsMethod(true);
            methods.add(funDecl);
        }

        if (isAtEnd())
            error("Unterminated class body");

        return new ClassDeclarationNode(className, extendsClass, methods);
    }

    private FunctionDeclarationNode funDecl() throws ParseError {
        Token idToken = this.peek();

        if (!match(TokenType.IDENTIFIER))
            error("No identifier specified for function declaration");

        if (!match(TokenType.PAREN_OPEN))
            error("Expected a '(' before function arguments");

        List<String> parameters = parameters();

        if (!match(TokenType.CURLY_BRACKET_OPEN))
            error("Expected a '{' to specify a function declaration");

        FunctionContext functionContext = new FunctionContext((String)idToken.literal);
        functionContext.setParameters(parameters);

        FunctionContext outerContext = this.currentScope;

        this.currentScope = functionContext;
        ExpressionNode block = block();
        this.currentScope = outerContext;

        return new FunctionDeclarationNode((String)idToken.literal, functionContext.getFrameDescriptor(), block);
    }

    private LocalVariableNode.VariableWriteNode varDecl() throws ParseError {
        Token identifierToken = peek();

        if (!match(TokenType.IDENTIFIER))
            error("No identifier in variable declaration");

        String varName = (String) identifierToken.literal;

        // Not a fan, I'd rather have this be handled in setLocal() but it's another class that doesn't have access to error()
        if (this.currentScope.isVarDefined(varName))
            error("Already a variable with this name in the current scope");

        if (match(TokenType.SEMICOLON))
            return LocalVariableNodeFactory.VariableWriteNodeGen.create(varName, this.currentScope.setLocal(varName), new NilLiteralNode());

        if (!match(TokenType.EQUALS))
            error("Expect an equals or semicolon after an identifier in a variable declaration");

        ExpressionNode assignedExpr = expression();

        if (!match(TokenType.SEMICOLON))
            error("Unterminated variable declaration");

        return LocalVariableNodeFactory.VariableWriteNodeGen.create(varName, this.currentScope.setLocal(varName), assignedExpr);
    }

    private ExpressionNode statement() throws ParseError {
        if (match(TokenType.FOR))
            return forStmt();
        else if (match(TokenType.IF))
            return ifStmt();
        else if (match(TokenType.PRINT))
            return printStmt();
        else if (match(TokenType.RETURN))
            return returnStmt();
        else if (match(TokenType.WHILE))
            return whileStmt();
        else if (match(TokenType.CURLY_BRACKET_OPEN))
            return block();
        else
            return exprStatement();
    }

    private ForNode forStmt() throws ParseError {
        // forStmt        → "for" "(" ( varDecl | exprStmt | ";" ) expression? ";" expression? ")" statement ;

        if (!match(TokenType.PAREN_OPEN))
            error("Expect parentheses after a for keyword");

        ExpressionNode initialization = null;
        if (!match(TokenType.SEMICOLON)) {
            if (match(TokenType.VAR))
                initialization = varDecl();
            else
                error("for statement expects an initializer or a ';'");
        }

        ExpressionNode termination = null;
        if (peek().type != TokenType.SEMICOLON)
            termination = expression();

        if (!match(TokenType.SEMICOLON))
            error("Expect at least one semicolon in for declaration");

        ExpressionNode increment = null;
        if (peek().type != TokenType.PAREN_CLOSE) {
            increment = expression();
        }

        if (!match(TokenType.PAREN_CLOSE))
            error("Unclosed parentheses after for declaration");

        ExpressionNode body = statement();
        return new ForNode(initialization, termination, increment, body);
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

    private WhileNode whileStmt() throws ParseError {
        // "while" "(" expression ")" statement ;
        if (!match(TokenType.PAREN_OPEN))
            error("No open parenthesis after while");

        ExpressionNode cond = expression();

        if (!match(TokenType.PAREN_CLOSE))
            error("No closed parenthesis after while condition");

        ExpressionNode statement = statement();

        return new WhileNode(cond, statement);
    }

    private PrintStatement printStmt() throws ParseError {
        ExpressionNode expr = exprStatement();
        return PrintStatementFactory.create(expr);
    }

    private BlockNode block() throws ParseError {
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

            if (assignee instanceof LocalVariableNode) {
                String varName = (((LocalVariableNode)assignee).getName());
                int slotId = ((LocalVariableNode)assignee).getSlotId();
                return LocalVariableNodeFactory.VariableWriteNodeGen.create(varName, slotId, assignment);
            }

            if (assignee instanceof ArgumentNode) {
                int argIdx = ((ArgumentNode)assignee).getSlotId();
                return ArgumentNodeFactory.ArgumentWriteNodeGen.create(argIdx, assignment);
            }

            if (assignee instanceof ObjectPropertyNode objectPropertyNode) {
                return ObjectPropertyNodeFactory.ObjectPropertyWriteNodeGen.create(objectPropertyNode.getClassExpr(), objectPropertyNode.getPropertyName(), assignment);
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
                    if (unary instanceof DoubleLiteralNode) {
                        double value = ((DoubleLiteralNode) unary).executeDouble(null);
                        return new DoubleLiteralNode(-value);
                    } else if  (unary instanceof IntLiteralNode) {
                        int value = ((IntLiteralNode) unary).executeInt(null);
                        return new IntLiteralNode(-value);
                    } else {
                        return NegateNodeFactory.create(unary);
                    }
                }
                default -> error("Should be unreachable");
            }
        }
        return call();
    }

    private ExpressionNode call() throws ParseError {
        ExpressionNode primary = primary();

        while (match(TokenType.PAREN_OPEN) || match(TokenType.DOT)) {
            if (previous().type == TokenType.PAREN_OPEN) {
                List<ExpressionNode> arguments = arguments();
                primary = new CallNode(new LookupNode(primary), arguments); // Shouldn't always have to look it up, but it's a start
            } else if (previous().type == TokenType.DOT) {
                if (!match(TokenType.IDENTIFIER))
                    error("Expected an identifier after a \".\"");

                primary = ObjectPropertyNodeFactory.ObjectPropertyReadNodeGen.create(primary, (String) previous().literal);
            }
        }

        return primary;
    }


    private ExpressionNode primary() throws ParseError {
        Token currentToken = advance();

        switch (currentToken.type) {
            case TRUE -> { return new TrueLiteralNode(); }
            case FALSE -> { return new FalseLiteralNode(); }
            case NIL -> { return new NilLiteralNode(); }
            case INT -> { return new IntLiteralNode((int) currentToken.literal); }
            case DOUBLE -> { return new DoubleLiteralNode((Double) currentToken.literal); }
            case STRING -> { return new StringLiteralNode((String) currentToken.literal); }
            case PAREN_OPEN -> {
                ExpressionNode expr = expression();
                if (!match(TokenType.PAREN_CLOSE))
                    error("Unclosed parentheses");
                return expr;
            }
            case IDENTIFIER -> {
                String identifierName = (String) currentToken.literal;

                if (!this.currentScope.getName().equals(ROOT_FUNCTION_NAME)) { // i.e. whether we are currently parsing a function
                    Integer argSlotId = this.currentScope.getParam(identifierName);
                    if (argSlotId != null)
                        return ArgumentNodeFactory.ArgumentReadNodeGen.create(argSlotId);
                }

                if (peek().type == TokenType.PAREN_OPEN) // A function call
                    return new FunctionNameLiteralNode((String) currentToken.literal);
                else
                    return resolveIdentifier(identifierName);
            }
            default -> error("Invalid expression: primary token of type " + currentToken.type);
        }

        return null; // Unreachable
    }

    private ExpressionNode resolveIdentifier(String identifierName) {
        if (this.currentScope.getLocal(identifierName) != null)
            return LocalVariableNodeFactory.VariableReadNodeGen.create(identifierName, this.currentScope.getLocal(identifierName));

        return new IdentifierNode(identifierName);
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
