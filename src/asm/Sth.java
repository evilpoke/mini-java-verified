package asm;

import static asm.Opcode.STH;

public class Sth extends Instruction {
    @Override
    public byte[] encode() {
        return new byte[]{STH.encode()};
    }


    @Override
    public void accept(AsmVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return "STH";
    }
}
