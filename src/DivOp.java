
class DivOp<T extends Integer> extends BinOp<T> {
  public DivOp(Expression<T> i1, Expression<T> i2) {
    super(i1, i2);
  }

  public T fun(T i1, T i2) {
    return (T) new Integer(i1 / i2);
  }

  public String toString() {
    return ("(" + op1.toString() + "/" + op2.toString() + ")");
  }

}