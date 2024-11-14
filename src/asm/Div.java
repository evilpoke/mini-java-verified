package asm;
import static asm.Opcode.*;

public class Div extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {DIV.encode()};
  }
  
  @Override
  public String toString() {
    return "DIV";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
