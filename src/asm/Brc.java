package asm;
import static asm.Opcode.*;

public class Brc extends Instruction {
  private int target;

  public int getTarget() {
    return target;
  }
  
  public Brc(int target) {
    this.target = target;
  }

  @Override
  public byte[] encode() {
    byte[] targetEnc = Instruction.encodeImm(target, 3);
    return new byte[] {BRC.encode(), targetEnc[0], targetEnc[1], targetEnc[2]};
  }
  
  @Override
  public String toString() {
    return BRC.toString() + " " + target;
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }
}
