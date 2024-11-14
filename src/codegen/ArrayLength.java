package codegen;

import codegen.Expression;
import codegen.ProgramVisitor;

public class ArrayLength extends Expression {


    private Expression array;

    public ArrayLength(Expression array) {
        super();
        this.array = array;
    }


    public Expression getArray(){
        return array;
    }


    @Override
    public void accept(ProgramVisitor visitor) {
        visitor.visit(this);
    }
}
