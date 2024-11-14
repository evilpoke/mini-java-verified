package testingstuff;


public class Node {
    // Speichert die Anzahl der Pinguine, die an diesem Knoten auf
    // Übernahme einer Kartoffel warten
    public int waiting;

    // Speichert die Temperatur der Kartoffel zur Übergabe von einem
    // zum nächsten Pinguin zwischen
    public int potato;

    public Node() {
        waiting = 0;
        potato = 0;
    }
}
