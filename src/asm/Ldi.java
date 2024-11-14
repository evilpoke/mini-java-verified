package asm;
import static asm.Opcode.*;

public class Ldi extends Instruction {
  private int value;
  
  public int getValue() {
    return value;
  }
  
  public Ldi(int value) {
    this.value = value;
  }

  @Override
  public byte[] encode() {
    byte[] valueEnc = Instruction.encodeImm(value, 4);
    return new byte[] {LDI.encode(), valueEnc[0], valueEnc[1], valueEnc[2]};
  }
  
  @Override
  public String toString() {
    return LDI.toString() + " " + value;
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
