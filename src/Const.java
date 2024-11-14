class Const<T> extends Expression<T> {
  // Boolesche oder Integer-Konstante
  private final T c;

  public Const(T c) {
    this.c = c;
  }

  public T evaluate() {
    return c;
  }

  public String toString() {
    return (c.toString());
  }
}