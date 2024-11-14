abstract class ZAExpression<S> {
  // Funktion zur Auswertung eines ggf. nicht-atomaren Ausdrucks mit Typen T
  // Die Unterausdruecke der ZExpression koennen ggf. einen anderen Typen haben als
  // T
  // Zum Beispiel relationale Operatoren
  public abstract S evaluate();
  
  public abstract String toString();
}
