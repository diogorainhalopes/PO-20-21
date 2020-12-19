package woo.exceptions;



/** Exception for reporting unauthorized supplier attempts. */
public class NotAuthorizedSupplierException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009200054L;
  
    /** Supplier key. */
    private String _key;
  
    /** @param key unauthorized key to report. */
    public NotAuthorizedSupplierException(String key) {
      _key = key;
    }

    /** @return key */
    public String getKey() {
        return _key;
    } 
}