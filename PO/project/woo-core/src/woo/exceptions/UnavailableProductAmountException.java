package woo.exceptions;

/** Launched when there's not enough product stock to create a sale/order */
public class UnavailableProductAmountException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201709021324L;

    /** Product key. */
    private String _key;

    /** Requested amount. */
    private int _requested;

    /** Available amount. */
    private int _available;

    /** 
     * @param key the requested key
     * @param requested
     * @param available
     */
    public UnavailableProductAmountException(String key, int requested, int available) {
        _key = key;
        _requested = requested;
        _available = available;
    }

    /**
     * @return String return the key
     */
    public String getKey() {
        return _key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        _key = key;
    }

    /**
     * @return int return the requested amount
     */
    public int getRequested() {
        return _requested;
    }

    /**
     * @param _requested the requested amount to set
     */
    public void setRequested(int requested) {
        _requested = requested;
    }

    /**
     * @return int return the available amount
     */
    public int getAvailable() {
        return _available;
    }

    /**
     * @param available the available amount to set
     */
    public void set_available(int available) {
        _available = available;
    }

}
