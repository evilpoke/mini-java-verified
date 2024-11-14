// unaeres Minus
final class ZAMinusOp<T extends Integer> extends ZAUnOp<T> {
  public ZAMinusOp(ZAExpression<T> o) {
    super(o);
  }

  public T fun(T o) {
    return (T) new Integer(-o);
  }

  @Override
  public String toString() {
    return "-" + "(" + op + ")";
  }
}