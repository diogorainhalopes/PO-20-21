package woo;

import java.io.Serializable;
import java.util.*;


public abstract class Product implements Serializable, Observable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Product's unique key */
    private final String _key;

    /** Product's price */
    private int _price;

    /** Product's critical stock value */
    private int _criticalValue;

    /** Product's stock */
    private int _stock;

    /** Product's associated supplier key */
    private String _keySupplier;

    /** Product's time limit to avoid penalty or get discount  */
    private int _N;

    private ArrayList<Observer> _observers = new ArrayList<Observer>();


    /**
     * @return String return the key
     */
    public String getKey() {
        return _key;
    }

    /**
     * @return int return the price
     */
    public int getPrice() {
        return _price;
    }

    /**
     * @param price the price to set
     */
    protected void setPrice(int price) {
        _price = price;
    }

    /**
     * @return int return the criticalValue
     */
    public int getCriticalValue() {
        return _criticalValue;
    }

    /**
     * @param criticalValue the criticalValue to set
     *
    public void setCriticalValue(int criticalValue) {
        _criticalValue = criticalValue;
    }*/

    /**
     * @param criticalValue the amount to add to the criticalValue
     */
    public void addCriticalValue(int criticalValue) {
        _criticalValue += criticalValue;
    }

    /**
     * @return int return the stock
     */
    public int getStock() {
        return _stock;
    }

    /**
     * @param stock the stock to set
     */
    protected void setStock(int stock) {
        _stock = stock;
    }

    /**
     * @param stock the stock to be added
     */
    public void addStock(int stock) {
        _stock += stock;

    }

    /**
     * @param price the incremental price to raised
     */
    public void raisePrice(int price) {
        _price += price;
    }

    /**
     * @return String return the supplier key
     */
    public String getKeySupplier() {
        return _keySupplier;
    }

    /**
     * @param stock the stock to set
     *
    public void setKeySupplier(String keySupplier) {
        _keySupplier = keySupplier;
    }*/

    /**
     * @return int Product's time limit to avoid penalty ot get discount
     */
    public int getN() {
        return _N;
    }

    /**
     * @param N product's time limit to avoid penalty or get discount
     */
    protected void setN(int N) {
        _N = N;
    }

    /**
     * @param observers set all observers interested in the product
     */
    protected void setObserverList(ArrayList<Observer> observers) {
        _observers = observers;
    }

    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param price the price to set
     * @param criticalValue the critical value to set
     * @param keySupplier the supplier key to set
     */
    Product(String key, int price, int criticalValue, String keySupplier) {
        _key = key;
        _price = price;
        _criticalValue = criticalValue;
        _keySupplier = keySupplier;
        _stock = 0;
    }
    
    /**
     * Add an interested observer to the notification list
     * @param o the observer to add
     */
    public void registerObserver(Observer o) { _observers.add(o); }

    /**
     * Remove an uninterested observer to the notification list
     * @param o the observer to remove
     */
    public void removeObserver(Observer o) {
        int i = _observers.indexOf(o);
        if (i >= 0) { _observers.remove(i); }
      }

    /**
     * Notify all interested observers on the notification list
     * @param o the observer to remove
     */
    public void notifyObservers(String notifType) {
        for (Observer observer: _observers) {
            observer.update(_key, notifType, _price);
        }
    }

    public boolean searchObserver(Observer o) {
        return _observers.contains(o);
    }

    /**
     * @return String Product toString
     */
    @Override
    public String toString() {
        return String.format("%s|%s|%d|%d|%d", _key, _keySupplier, 
            _price, _criticalValue, _stock);     
    }

    
}
