package lox.objects;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public class Nil implements TruffleObject {
    public static final Nil INSTANCE = new Nil();

    @ExportMessage
    boolean isNull() {
        return true;
    }

    @Override
    public String toString() {
        return "nil";
    }
}
