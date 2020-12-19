package woo;

/**
 * Observable interface for ObserverPattern
 */
public interface Observable {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(String notifType);
    public boolean searchObserver(Observer o);
}




