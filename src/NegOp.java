
// unaerer logischer Operator
class NegOp<T extends Boolean> extends UnOp<T> {
  public NegOp(Expression<T> b) {
    super(b);
  }

  public T fun(T b) {
    return (T) new Boolean(!b);
  }

  public String toString() {
    return ("!" + op.toString());
  }
}