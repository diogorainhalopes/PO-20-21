package woo.exceptions;

/** Launched when the given supplier key already exists. */
public class NonUniqueSupplierKeyException extends Exception {
    
    /** Serial number for serialization. */
    private static final long serialVersionUID = 201709021324L;

    /** Supplier key */
    private String _key;

    /**
    * @param key
    */
    public NonUniqueSupplierKeyException(String key) {
        _key = key;
    }

    /** @return key */
    public String getKey() {
        return _key;
    }
}
