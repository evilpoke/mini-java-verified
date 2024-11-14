package asm;
import static asm.Opcode.*;

public class Not extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {NOT.encode()};
  }
  
  @Override
  public String toString() {
    return "NOT";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
