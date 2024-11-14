package asm;
import static asm.Opcode.*;

public class Out extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {OUT.encode()};
  }
  
  @Override
  public String toString() {
    return OUT.toString();
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
