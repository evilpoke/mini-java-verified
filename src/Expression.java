abstract class Expression<T> {
  // Funktion zur Auswertung eines ggf. nicht-atomaren Ausdrucks vom Typen T
  public abstract T evaluate();
}