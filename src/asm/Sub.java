package asm;
import static asm.Opcode.*;

public class Sub extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {SUB.encode()};
  }
  
  @Override
  public String toString() {
    return "SUB";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
