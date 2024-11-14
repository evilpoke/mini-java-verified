
// Unaere Operatoren werden spaeter spezialisiert in
// arithmetisch: ZMinusOp;
// logisch:      ZNegOp
abstract class ZAUnOp<T> extends ZAExpression<T> {
  // nur 1 Operand vom Typen T
  // Rueckgabewert der fun-Funktion ebenfalls vom Typen T
  protected ZAExpression<T> op;

  public ZAUnOp(ZAExpression<T> o) {
    op = o;
  }

  // Abstrakte Funktion fun zur Auswertung mit einem atomaren Ausdruck
  // wird spaeter zu unaerem Minus sowie logischer Negation spezialisiert
  protected abstract T fun(T __);

  // Funktion zur Auswertung mit ggf. einem nicht-atomaren Argument
  public T evaluate() {
    return fun(op.evaluate());
  }
}
