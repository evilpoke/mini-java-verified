public class KrebsException extends Exception{
    @Override
    public String toString() {
        return "Krebs";
    }

    @Override
    public String getMessage() {
        return "Sensei mutiert";
    }

    @Override
    public int hashCode() {
        return 404;
    }


}