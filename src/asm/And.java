package asm;
import static asm.Opcode.*;

public class And extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {AND.encode()};
  }
  
  @Override
  public String toString() {
    return "AND";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
