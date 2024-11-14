package asm;
import static asm.Opcode.*;

public class Halt extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {HALT.encode()};
  }
  
  @Override
  public String toString() {
    return "HALT";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
