package lox.objects;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;

@ExportLibrary(InteropLibrary.class)
public final class LoxFunction implements TruffleObject {
    private final String name;

    public final CallTarget callTarget;

    public LoxFunction(CallTarget callTarget, String name) {
        this.callTarget = callTarget;
        this.name = name;
    }

    @Override
    public String toString() {
        return "function " + name;
    }
}
