
final class ZANegOp<T extends Boolean> extends ZAUnOp<T> {
  public ZANegOp(ZAExpression<T> b) {
    super(b);
  }

  public T fun(T b) {
    return (T) new Boolean(!b);
  }

  @Override
  public String toString() {
    return "!" + op;
  }
}