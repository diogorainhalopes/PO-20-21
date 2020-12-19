package woo;

/**
 * Observer interface for ObserverPattern
 */
public interface Observer {
    public void update(String prodName, String notifType, int price);
}