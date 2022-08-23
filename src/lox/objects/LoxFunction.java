package lox.objects;

import com.oracle.truffle.api.CallTarget;

// TODO needs to be implement TruffleObject for interop
public final class LoxFunction {
    public final CallTarget callTarget;

    public LoxFunction(CallTarget callTarget) {
        this.callTarget = callTarget;
    }
}
