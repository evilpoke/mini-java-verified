package asm;

public class IllegalUnlockException extends InterpreterException {
    public IllegalUnlockException() {
        super("Trying to unlock a already unlocked heap object");
    }
}
