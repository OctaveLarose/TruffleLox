package lox;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.TruffleLanguage;
import lox.nodes.LoxNode;
import lox.nodes.LoxRootNode;
import lox.parser.Parser;

@TruffleLanguage.Registration(id = "tlox", name = "TruffleLox")
public class LoxLanguage extends TruffleLanguage<LoxLanguage> {

    @CompilationFinal
    private static LoxLanguage current;

    public static LoxLanguage getCurrent() {
        return current;
    }

    @Override
    protected LoxLanguage createContext(Env env) {
        current = this;
        return current;
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        LoxNode exprNode = new Parser(request.getSource().getReader()).parse();
        LoxRootNode rootNode = new LoxRootNode(exprNode);
        return rootNode.getCallTarget();
    }
}