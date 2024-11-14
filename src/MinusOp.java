// unaerer arithmetischer Operator
class MinusOp<T extends Integer> extends UnOp<T> {
  public MinusOp(Expression<T> b) {
    super(b);
  }

  public T fun(T b) {
    return (T) new Integer(-b);
  }

  public String toString() {
    return ("(-" + op.toString() + ")");
  }

}