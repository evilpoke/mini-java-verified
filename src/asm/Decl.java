package asm;
import static asm.Opcode.*;

public class Decl extends Instruction {
  private int count;

  public int getCount() {
    return count;
  }
  
  public Decl(int count) {
    this.count = count;
  }

  @Override
  public byte[] encode() {
    byte[] countEnc = Instruction.encodeImm(count, 1);
    return new byte[] {DECL.encode(), countEnc[0]};
  }
  
  @Override
  public String toString() {
    return DECL.toString() + " " + count;
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }
}
