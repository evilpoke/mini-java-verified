package asm;
import static asm.Opcode.*;

public class Add extends Instruction {

  @Override
  public byte[] encode() {
    return new byte[] {ADD.encode()};
  }
  
  
  @Override
  public String toString() {
    return "ADD";
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
