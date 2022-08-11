package lox.objects;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.interop.TruffleObject;

// TODO needs to be implement TruffleObject for interop
public final class FunctionObject {
    public final CallTarget callTarget;

    public FunctionObject(CallTarget callTarget) {
        this.callTarget = callTarget;
    }
}
