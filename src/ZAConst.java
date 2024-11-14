
// 2. Konstanten
final class ZAConst<T> extends ZAExpression<T> {
  // Boolesche oder Integer-Konstante
  private final T c;

  public ZAConst(T c) {
    this.c = c;
  }

  public T evaluate() {
    return c;
  }

  @Override
  public String toString() {
    return c.toString();
  }
}
