package asm;

public class IllegalHeapAccessException extends InterpreterException {
    public IllegalHeapAccessException(int i) {
        super("Invalid heap access at "+i);
    }
}
