package asm;
import static asm.Opcode.*;

public class Mod extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {MOD.encode()};
  }
  
  @Override
  public String toString() {
    return "MOD";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
