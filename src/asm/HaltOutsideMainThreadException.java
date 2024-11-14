package asm;

public class HaltOutsideMainThreadException extends InterpreterException {
    public HaltOutsideMainThreadException() {
        super("Halt instruction is called outside main thread");
    }
}
