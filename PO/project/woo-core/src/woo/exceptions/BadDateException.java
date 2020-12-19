package woo.exceptions;

/** Launched when the given time to advance is negative. */
public class BadDateException extends Exception {
        
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192335L;

    /** Client key */
    private int _date;

    /**
    * @param name
    */
    public BadDateException(int date) {
        _date = date;
    }

    /** @return key */
    public int getDate() {
        return _date;
    } 
}
