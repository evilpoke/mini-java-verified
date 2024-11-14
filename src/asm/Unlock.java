package asm;

import static asm.Opcode.UNLOCK;

public class Unlock extends Instruction {


    @Override
    public byte[] encode() {
        return new byte[]{UNLOCK.encode()};
    }

    @Override
    public void accept(AsmVisitor visitor) {
        visitor.visit(this);
    }

}
