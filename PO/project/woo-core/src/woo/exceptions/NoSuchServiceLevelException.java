package woo.exceptions;

/** Launched when the given supplier key doesn't exist. */
public class NoSuchServiceLevelException extends Exception{
        
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192335L;

    /** Client key */
    private String _key;

    /**
    * @param key
    */
    public NoSuchServiceLevelException(String key) {
        _key = key;
    }

    /** @return key */
    public String getKey() {
        return _key;
    }
}
