package woo;

import java.io.*;


public class Supplier implements Serializable {

    private static final long serialVersionUID = 202009192006L;


    private final String _key;
    private String _name;
    private String _address;
    private boolean _trade;


    /**
     * @return String return the key
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
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * @return String return the address
     */
    public String getAddress() {
        return _address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        _address = address;
    }

    /**
     * @return boolean return the trade
     */
    public boolean getTrade() {
        return _trade;
    }

    /**
     * @param trade the trade to set
     */
    public void setTrade(boolean trade) {
        _trade = trade;
    }

    /**
     * Trade switch
     */
    public void toggleTrade() {
        _trade = !_trade;
    }


    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param name the name to set
     * @param address the address to set
     */
    Supplier(String key, String name, String address) {
        _key = key;
        _name = name;
        _address = address;
        _trade = true;
    }


    @Override
    public String toString() {
        String str = String.format("%s|%s|%s|", _key, _name, 
            _address);     
        if (_trade) 
            return str + "y";
        else
            return str + "n";
    }



}
