package woo;

import java.io.Serializable;

public class Notification implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** The product's key for this notification */
    private String _prodKey;
    
    /** The product's price for this notification */
    private int _price;

    /** The product's notification type for this notification */
    private String _notifType;




    Notification(String prodKey, String notifType, int price) {
        _prodKey = prodKey;
        _price = price;
        _notifType = notifType;
    }

    /**
     * @return String return the prodKey
     */
    public String getProdKey() {
        return _prodKey;
    }

    /**
     * @param prodKey the prodKey to set
     *
    public void setProdKey(String prodKey) {
        _prodKey = prodKey;
    }*/

    /**
     * @return int return the price
     */
    public int getPrice() {
        return _price;
    }

    /**
     * @param price the price to set
     *
    public void setPrice(int price) {
        _price = price;
    }*/

    /**
     * @return String return the notifType
     */
    public String getNotifType() {
        return _notifType;
    }

    /**
     * @param notifType the notifType to set
     *
    public void setNotifType(String notifType) {
        _notifType = notifType;
    }*/

}