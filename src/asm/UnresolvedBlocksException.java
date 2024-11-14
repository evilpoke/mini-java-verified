package asm;

public class UnresolvedBlocksException extends InterpreterException {
    public UnresolvedBlocksException() {
        super("At least one heapobject is still blocked by a thread");
    }
}
