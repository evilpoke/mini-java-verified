
// 4. Konkrete logische Operatoren
final class ZAOrOp<T extends Boolean, S extends Boolean> extends ZABinOp<T, S> {
  public ZAOrOp(ZAExpression<T> b1, ZAExpression<T> b2) {
    super(b1, b2);
  }

  public S fun(T b1, T b2) {
    return (S) new Boolean(b1 || b2);
  }

  @Override
  public String toString() {
    return "(" + op1 + "||" + op2 + ")";
  }
}