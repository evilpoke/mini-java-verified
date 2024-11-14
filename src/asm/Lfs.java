package asm;
import static asm.Opcode.*;

public class Lfs extends Instruction {
  private int index;
  
  public int getIndex() {
    return index;
  }
  
  public Lfs(int index) {
    this.index = index;
  }

  @Override
  public byte[] encode() {
    return new byte[] {LFS.encode()};
  }
  
  @Override
  public String toString() {
    return LFS.toString() + " " + index;
  }

  @Override
  public void accept(AsmVisitor visitor) {
    visitor.visit(this);
  }

}
