package rareshroom;

import com.oracle.truffle.api.TruffleLanguage;

@TruffleLanguage.Registration(id = "RS", name = "rareshroom")
public class ShroomLanguage extends TruffleLanguage<ShroomLanguage> {
    @Override
    protected ShroomLanguage createContext(Env env) {
        return null;
    }
}