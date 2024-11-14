
// 1. Abstrakte zwei- und einstellige Operatoren
// ZExpression hat drei Unterklassen: Binaere, unaere und nullstellige Operatoren
// Binaere Operatoren werden spaeter spezialisiert in
//  arithmetisch: ZAddOp, ZSubOp, ZMulOp, ZDivOp; 
//  logisch:      ZAndOp, ZOrOp;
//  relational:   GTOp, LTOp, EQOp
abstract class ZABinOp<T, S> extends ZAExpression<S> {
  // Operanden 1 und 2 vom Typen T
  // Rueckgabewert der fun-Funktion vom Typen S
  // S und T sind unterschiedlich nur im Fall relationaler Operatoren: die sind
  // (Int X Int -> Bool)
  protected ZAExpression<T> op1, op2;

  public ZABinOp(ZAExpression<T> o1, ZAExpression<T> o2) {
    op1 = o1;
    op2 = o2;
  }

  // Abstrakte Funktion fun zur Auswertung mit zwei atomaren Argumenten
  // wird spaeter zu +,*,-,/ sowie &&,||,! sowie <,>,== spezialisiert
  // so kann evaluate() unabhaengig vom konkreten Operator implementiert werden
  protected abstract S fun(T _1, T _2);

  // Funktion zur Auswertung mit ggf. einem nicht-atomaren Argument
  public S evaluate() {
    return fun(op1.evaluate(), op2.evaluate());
  }
}
