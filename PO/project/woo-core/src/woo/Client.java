package woo;

import java.io.*;
import java.util.*;


public class Client implements Serializable, Observer, DisplayNotification {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Client's unique key */
    private final String _key;

    /** Client's name */
    private String _name;

    /** Client's address */
    private String _address;

    /** Client's company points */
    private double _points;

    /** Client's total purchases value */
    private double _purchasesValue;

    /** Client's total purchases paid */
    private double _purchasesPaid;

    /** Client's company status */
    private ClientState _state;

    /** Clients's notification delivery method */
    private String _notifDelivery;

    /** Client's product notifications */
    private ArrayList<Notification> _notifications = new ArrayList<Notification>();

    /** Points needed to promote status */
    private final int NormalToSelection = 2000;
    private final int SelectionToElite = 25000;

    /** Days after deadline needed to demote status */
    private final int EliteToSelection = -15;
    private final int SelectionToNormal = -2;

    /** % of points lost after demotion */
    private final double EliteLostPoints = 0.75;
    private final double SelectionLostPoints = 0.9;

    /** Point conversion rate */
    private final double ConversionRate = 10;

    /**
     * Abstract state class.
     *
     * This class is internal so that it has access to the client's
     * internal state. Actual states are subclasses which must use this class'
     * protected interface.
     */
    public abstract class ClientState implements Serializable{
        /** Serial number for serialization. */
        private static final long serialVersionUID = 202009192006L;

        /** Promotion behavior */
        public abstract void promote();

        /** Demotion behavior */
        public abstract void demote();

        /** @return client's status. */
        public abstract String status();

         /**
         * Define the client's new state.
         *
         * @param newState
         *            the new state.
         */
        protected void setState(ClientState newState) {
            _state = newState;
        }

        /**
         * This method is needed so that new states can be created.
         *
         * @return the client.
         */
        protected Client getClient() {
            return Client.this;
        }
    }


    /**
     * Update Status
     * @param pricePaid the price paid
     * @param timeDifference days before/after the deadline
     */
    public void updateStatus(double pricePaid, int timeDifference) {
        if(timeDifference >= 0) {
            addPoints(pricePaid * ConversionRate);
            if(getStatus().equals("NORMAL")) {
                if(_points > NormalToSelection) {
                    _state.promote();
                }
            }
            if(getStatus().equals("SELECTION")) {
                if(_points > SelectionToElite) {
                    _state.promote();
                }
            }
            return;
        }

        if(getStatus().equals("ELITE")) {
            if(timeDifference < EliteToSelection) {
                addPoints(-(EliteLostPoints*_points));
                _state.demote();
            }
        }
        else if(getStatus().equals("SELECTION")) {
            if(timeDifference < SelectionToNormal) {
                addPoints(-(SelectionLostPoints*_points));
                _state.demote();
            }
        }
    }





    /**
     * @return String return the name
     */
    public String getKey() {
        return _key;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name the name to set
     *
    public void setName(String name) {
        _name = name;
    }*/

    /**
     * @return String return the address
     */
    public String getAddress() {
        return _address;
    }

    /**
     * @param address the address to set
     *
    public void setAddress(String address) {
        _address = address;
    }*/
    

    /**
     * @return String return the status
     */
    public String getStatus() {
        return _state.status();
    }

    /**
     * @return int return the points
     */
    public double getPoints() {
        return _points;
    }

    /**
     * @param points the points to set
     *
    public void setPoints(double points) {
        _points = points;
    }*/

    /**
     * @param points the points to add
     */
    public void addPoints(double points) {
        _points += points;
    }


    /**
     * @return String return the value of ordered products
     */
    public double getPurchasesValue() {
        return _purchasesValue;
    }

    /**
     * @param purchasesValue set the value of ordered products
     *
    public void setPurchasesValue(double purchasesValue) {
        _purchasesValue = purchasesValue;
    }*/

    /**
     * @return String return the value of paid products
     */
    public double getPurchasesPaid() {
        return _purchasesPaid;
    }

    /**
     * @param purchasesPaid set the value of paid products
     *
    public void setPurchasesPaid(double purchasesPaid) {
        _purchasesPaid = purchasesPaid;
    }*/

    /**
     * @param purchasesPaid to add to the value of paid products
     */
    public void addPurchasesPaid(double purchasesPaid) {
        _purchasesPaid += purchasesPaid;
    }

    /**
     * @param purchasesValue to add to the value of paid products
     */
    public void addPurchasesValue(double purchasesValue) {
        _purchasesValue += purchasesValue;
    }

    /**
     * @param notifDelivery set the notification delivery method
     */
    public void setNotificationDelivery(String notifDelivery) {
        _notifDelivery = notifDelivery;
    }

    /**
     * @return String return notification delivery method
     */
    public String getNotificationDelivery() {
        return _notifDelivery;
    }




    /**
     * @return ArrayList<Notification> return the notifications list
     */
    public ArrayList<Notification> getNotifications() {
        return _notifications;
    }




    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param name the name to set
     * @param address the address to set
     */
    Client(String key, String name, String address) {
        _key = key;
        _name = name;
        _address = address;
        _points = 0;
        _state = new NormalState(this);
        _notifDelivery = "";
    }

    /**
     * @return String Client toString
     */
    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%d|%d", _key, _name, 
            _address, _state.status(), (int) Math.round(_purchasesValue),
                (int) Math.round(_purchasesPaid));
    }

    public void update(String prodKey, String notifType, int price) {
        Notification n = new Notification(prodKey, notifType, price);
        _notifications.add(n);
    }

    public String display() {
        String str = "";
        if(_notifDelivery.equals("")) {
            for(Notification n : _notifications) {
                str = String.format("%s\n%s|%s|%d", str, n.getNotifType(), 
                    n.getProdKey(), n.getPrice()); 
            }
        }
        return str;
    }







}


