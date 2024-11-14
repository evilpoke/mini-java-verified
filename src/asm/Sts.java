package asm;
import static asm.Opcode.*;

public class Sts extends Instruction {
  private int index;
  
  public int getIndex() {
    return index;
  }
  
  public Sts(int index) {
    this.index = index;
  }

  @Override
  public byte[] encode() {
    return new byte[] {STS.encode(), (byte)index};
  }
  
  @Override
  public String toString() {
    return STS.toString() + " " + index;
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
