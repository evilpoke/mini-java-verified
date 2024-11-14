package asm;

import static asm.Opcode.*;

public class Join extends Instruction{
    @Override
    public byte[] encode() {
        return new byte[] {JOIN.encode()};
    }

    @Override
    public String toString() {
        return "JOIN";
    }

    @Override
    public void accept(AsmVisitor visitor) {
        visitor.visit(this);
    }
}
