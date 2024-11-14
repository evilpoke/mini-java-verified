package codegen;

import codegen.Expression;
import codegen.ProgramVisitor;

public class ArrayAccess  extends Expression {

    private Expression arrayindexaccess;
    private Expression arrayadress;

    public ArrayAccess() {
        super();
    }

    public ArrayAccess(Expression array, Expression index) {
        this.arrayindexaccess =index;
        this.arrayadress = array;
    }

    public Expression getIndex(){
        return  arrayindexaccess;
    }

    public Expression getArray(){
        return  arrayadress;
    }






    @Override
    public void accept(ProgramVisitor visitor) {
        visitor.visit(this);
    }
}
