package asm;

import static asm.Opcode.LFH;

public class Lfh extends Instruction {
    @Override
    public byte[] encode() {
        return new byte[]{LFH.encode()};
    }

    @Override
    public void accept(AsmVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LFH";
    }


}
