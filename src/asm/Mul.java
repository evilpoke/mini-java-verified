package asm;
import static asm.Opcode.*;

public class Mul extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {MUL.encode()};
  }
  
  @Override
  public String toString() {
    return "MUL";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
