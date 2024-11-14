package asm;

public class DeadlockException extends InterpreterException {
    public DeadlockException() {
        super("All threads are waiting with join(): DEADLOCK");
    }
}
