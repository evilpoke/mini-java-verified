package codegen;


public class Program {
  private Function[] functions;
  
  public Function[] getFunctions() {
    return functions;
  }
  
  
  public Program(Function[] functions) {
    super();
    this.functions = functions;
  }

  public void accept(ProgramVisitor visitor) {
    visitor.visit(this);
  }
}
