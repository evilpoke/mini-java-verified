package asm;

import static asm.Opcode.*;

public class Fork extends Instruction{

    private int argCount;

    public int getArgCount() {
        return argCount;
    }

    public Fork(int argCount) {
        this.argCount = argCount;
    }

    @Override
    public byte[] encode() {
        return new byte[] {FORK.encode()};
    }

    @Override
    public String toString() {
        return FORK.toString() + " " + argCount;
    }

    @Override
    public void accept(AsmVisitor visitor) {
        visitor.visit(this);
    }


}
