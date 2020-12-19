package woo.exceptions;

/** Exception for unknown transaction keys. */
public class NoSuchTransactionKeyException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192008L;
  
    /** Unknown key. */
    private int _transKey;
  
    /** @param transKey Unknown key to report. */
    public NoSuchTransactionKeyException(int transKey) {
      _transKey = transKey;
    }


  /** @return key */
  public int getTranskKey() {
    return _transKey;
}
}










