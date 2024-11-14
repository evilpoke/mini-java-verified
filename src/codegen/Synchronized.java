package codegen;

public class Synchronized extends Statement {
  private Expression mutex;
  
  public Expression getMutex() {
    return mutex;
  }
  
  private Statement[] criticalSection;
  
  public Statement[] getCriticalSection() {
    return criticalSection;
  }

  public Synchronized(Expression mutex, Statement[] criticalSection) {
    super();
    this.mutex = mutex;
    this.criticalSection = criticalSection;
  }

  @Override
  public void accept(ProgramVisitor visitor) {
    visitor.visit(this);
  }



}
