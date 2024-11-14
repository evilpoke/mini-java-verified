// 5. Konkrete relationale Operatoren
// einziger Grund, warum es zwei Typvariablen gibt!
final class GTOp<T extends Integer, S extends Boolean> extends ZABinOp<T, S> {
  public GTOp(ZAExpression<T> i1, ZAExpression<T> i2) {
    super(i1, i2);
  }

  public S fun(T i1, T i2) {
    return (S) new Boolean(i1 > i2);
  }

  @Override
  public String toString() {
    return op1 + ">" + op2;
  }
}
