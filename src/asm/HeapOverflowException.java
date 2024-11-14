package asm;

public class HeapOverflowException  extends InterpreterException {
    public HeapOverflowException() {
        super("Arraysize exceeds heap memory capabilities");
    }
}
