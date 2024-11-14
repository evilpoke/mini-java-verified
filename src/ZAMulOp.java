
final class ZAMulOp<T extends Integer, S extends Integer> extends ZABinOp<T, S> {
  public ZAMulOp(ZAExpression<T> i1, ZAExpression<T> i2) {
    super(i1, i2);
  }

  public S fun(T i1, T i2) {
    return (S) new Integer(i1 * i2);
  }

  @Override
  public String toString() {
    return "(" + op1 + "*" + op2 + ")";
  }
}
