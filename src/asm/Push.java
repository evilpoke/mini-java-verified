package asm;
import static asm.Opcode.*;

public class Push extends Instruction {
  private int register;
  
  public int getRegister() {
    return register;
  }
  
  public Push(int register) {
    this.register = register;
  }

  @Override
  public byte[] encode() {
    return new byte[] {PUSH.encode(), (byte)register};
  }
  
  @Override
  public String toString() {
    return PUSH.toString() + " " + register;
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }
}
