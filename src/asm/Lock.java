package asm;

import static asm.Opcode.*;

public class Lock extends Instruction {


    @Override
    public byte[] encode() {
        return new byte[]{LOCK.encode()};
    }

    @Override
    public void accept(AsmVisitor visitor) {
        visitor.visit(this);
    }
}
