package asm;

public abstract class Instruction {
  public static byte[] encodeImm(int immediate, int size) {
    byte[] result = new byte[size];
    for(int i = 0; i < size; i++)
      result[i] = (byte)(immediate >>> 8*i);
    return result;
  }
  
  public abstract byte[] encode();
  
  public abstract void accept(AsmVisitor visitor);
}
