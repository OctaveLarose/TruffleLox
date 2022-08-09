package lox;

import com.oracle.truffle.api.TruffleLanguage;

@TruffleLanguage.Registration(id = "RS", name = "TruffleLox")
public class LoxLanguage extends TruffleLanguage<LoxLanguage> {
    @Override
    protected LoxLanguage createContext(Env env) {
        return null;
    }
}