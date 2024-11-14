abstract class BinOp<T> extends Expression<T> {
  // Operand 1 und 2
  protected Expression<T> op1, op2;

  public BinOp(Expression<T> o1, Expression<T> o2) {
    op1 = o1;
    op2 = o2;
  }

  // Funktion zur Auswertung mit zwei atomaren Argumenten
  // wird spaeter zu +,*,-,/ sowie &&,|| spezialisiert
  protected abstract T fun(T a1, T a2);

  // Funktion zur Auswertung mit ggf. einem nicht-atomaren Argument
  // auf Basis der hier noch nicht definierten (aber deklarierten) Funktion fun
  public T evaluate() {
    return fun(op1.evaluate(), op2.evaluate());
  }
}