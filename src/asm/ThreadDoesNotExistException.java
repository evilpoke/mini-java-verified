package asm;

public class ThreadDoesNotExistException extends InterpreterException {
    public ThreadDoesNotExistException() {
        super("Nonexisting thread can not be joined on");
    }
}
