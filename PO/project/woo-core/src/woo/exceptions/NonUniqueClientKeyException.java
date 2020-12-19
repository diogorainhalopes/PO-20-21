package woo.exceptions;

/** Launched when the given client key already exists. */
public class NonUniqueClientKeyException extends Exception {
    
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192335L;

    /** Client key */
    private String _key;

    /**
    * @param key
    */
    public NonUniqueClientKeyException(String key) {
        _key = key;
    }

    /** @return key */
    public String getKey() {
        return _key;
    }
}
