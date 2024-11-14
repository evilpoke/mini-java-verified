
abstract class UnOp<T> extends Expression<T> {
  // nur 1 Operand
  protected Expression<T> op;

  public UnOp(Expression<T> o) {
    op = o;
  }

  // Funktion zur Auswertung mit einem atomaren Ausdruck
  // wird spaeter zu unaerem Minus sowie logischer Negation spezialisiert
  protected abstract T fun(T a1);

  // Funktion zur Auswertung mit ggf. einem nicht-atomaren Argument
  public T evaluate() {
    return fun(op.evaluate());
  }
}