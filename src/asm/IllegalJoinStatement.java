package asm;

public class IllegalJoinStatement extends InterpreterException {
    public IllegalJoinStatement() {
        super("Either the jointhread does not exist or it's not meant to be used (e.g. targeting itself or mainthread)");
    }
}
