package asm;

public class PendingThreadException extends InterpreterException {
    public PendingThreadException() {
        super("Threads apart from main-Thread haven't been closed!");
    }
}
