package asm;

public enum Opcode {
  NOP(0x00),
  ADD(0x01),
  SUB(0x02),
  MUL(0x03),
  MOD(0x04),
  DIV(0x05),
  AND(0x10),
  OR(0x11),
  NOT(0x12),
  SHL(0x13),

  FORK(0xA1),
  JOIN(0xA2),
  LOCK(0xA3),
  UNLOCK(0xA4),

  LDI(0x20),
  LFS(0x30),
  STH(0x33),
  STS(0x31),
  LFH(0x32),
  CALL(0x40),
  RETURN(0x41),
  BRC(0x42),
  CMP(0x50), // EQ und LT
  IN(0x60),
  OUT(0x61),
  DECL(0x70),
  PUSH(0x80),

  POP(0x81),
  HALT(0x7F),
  ALLOC(0xB1);


  private byte encoding;
  
  public byte encode() {
    return encoding;
  }

  private Opcode(int encoding) {
    this.encoding = (byte)encoding;
  }
}
