package codegen;

import codegen.Expression;
import codegen.ProgramVisitor;
import codegen.Statement;

public class ArrayIndexAssignment extends Statement {

    private Expression arrayindexaccess;
    private Expression arrayadress;
    private Expression assignedexpression;

    public ArrayIndexAssignment(Expression variable, Expression i, Expression assignedexpression) {
        this.assignedexpression = assignedexpression;
        this.arrayadress = variable;
        this.arrayindexaccess= i;
    }

    public Expression getIndex(){
        return  arrayindexaccess;
    }

    public Expression getArray(){
        return  arrayadress;
    }

    public Expression getExpression(){
        return assignedexpression;
    }





    @Override
    public void accept(ProgramVisitor visitor) {
        visitor.visit(this);
    }
}
