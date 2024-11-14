
// binaere logische Operatoren
class OrOp<T extends Boolean> extends BinOp<T> {
  public OrOp(Expression<T> b1, Expression<T> b2) {
    super(b1, b2);
  }

  public T fun(T b1, T b2) {
    return (T) new Boolean(b1 || b2);
  }

  public String toString() {
    return ("(" + op1.toString() + "||" + op2.toString() + ")");
  }
}
