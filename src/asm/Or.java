package asm;
import static asm.Opcode.*;

public class Or extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {OR.encode()};
  }
  
  @Override
  public String toString() {
    return "OR";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
