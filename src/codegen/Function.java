package codegen;

import testingstuff.Fun;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;

public class Function {
  private String name;
  private Parameter[] parameters;
  private Declaration[] declarations;
  private Statement[] statements;
  private Type returntype;

  public Function(Type returntype, String name, Parameter[] parameters, Declaration[] declarations, Statement[] statements) {
    super();
    this.returntype = returntype;
    this.name = name;
    this.parameters = parameters;
    this.declarations = declarations;
    this.statements = statements;


  }

  public Type getReturntype(){
    return returntype;
  }

  public String getName() {
    return name;
  }


  public Parameter[] getParameters() {
    return parameters;
  }


  public Declaration[] getDeclarations() {
    return declarations;
  }


  public Statement[] getStatements() {
    return statements;
  }

  public Function(String name, String[] intparameters, Declaration[] declarations,
      Statement[] statements) {
    super();
    this.name = name;
    parameters = new Parameter[intparameters.length];
    for(int i = 0; i < parameters.length; i++){
      parameters[i] = new Parameter(Type.Int,intparameters[i]);
    }
    this.declarations = declarations;
    this.statements = statements;
    this.returntype = Type.Int;

  }
  public Function(String name, Parameter[] params, Declaration[] declarations, Statement[] statements){
    super();
    this.name = name;
    this.parameters = params;
    this.declarations = declarations;
    this.statements = statements;
    this.returntype = Type.Int;
  }


  public void accept(ProgramVisitor visitor) {
    visitor.visit(this);
  }
}
