package woo.exceptions;

/** Launched when the given client key already exists. */
public class NonUniqueProductKeyException extends Exception {
    
    /** Serial number for serialization. */
    private static final long serialVersionUID = 201709021324L;

    /** Client key */
    private String _key;

    /**
    * @param key
    */
    public NonUniqueProductKeyException(String key) {
        _key = key;
    }

    /** @return key */
    public String getKey() {
        return _key;
    }
}
