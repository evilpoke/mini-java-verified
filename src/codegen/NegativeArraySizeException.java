package codegen;

import asm.InterpreterException;

public class NegativeArraySizeException extends InterpreterException {
    public NegativeArraySizeException() {
        super("Array instantiated with negative length");
    }
}
