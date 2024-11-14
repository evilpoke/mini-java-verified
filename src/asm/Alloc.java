package asm;

import static asm.Opcode.ALLOC;

public class Alloc extends Instruction {
    @Override
    public byte[] encode() {
        return new byte[] {ALLOC.encode()};
    }

    @Override
    public void accept(AsmVisitor visitor) {

        visitor.visit(this);
    }


    public String toString(){
        return "ALLOC";
    }
}
