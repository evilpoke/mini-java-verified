package codegen;

public abstract class Condition {
  public abstract void accept(ProgramVisitor visitor);
}
