package lox;

import com.oracle.truffle.api.TruffleLanguage.ContextReference;
import com.oracle.truffle.api.nodes.Node;

public final class LoxContext {
    private static final ContextReference<LoxContext> REFERENCE = ContextReference.create(LoxLanguage.class);
    public final GlobalScope globalScope;

    public LoxContext(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    public static LoxContext get(Node node) {
        return REFERENCE.get(node);
    }
}
