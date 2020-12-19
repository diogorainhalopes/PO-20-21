package woo;



public class Container extends Box {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Container service level enumeration */
    public enum ServiceLevel {
        B4,
        C4,
        C5,
        DL
    }

    /** Container's service level */
    private ServiceLevel _serviceLevel;



    /**
     * @return String return the serviceLevel
     */
    public ServiceLevel getServiceLevel() {
        return _serviceLevel;
    }
    


    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param price the price to name
     * @param criticalValue the critical value to set
     * @param keySupplier the supplier key to set
     * @param serviceLevel the service level to set
     */
    Container(String key, int price, int criticalValue, String keySupplier, 
        String serviceType, String serviceLevel) {
        super(key, price, criticalValue, keySupplier, serviceType);
        if (serviceLevel.equals("B4")) _serviceLevel = ServiceLevel.B4;
        if (serviceLevel.equals("C4")) _serviceLevel = ServiceLevel.C4;
        if (serviceLevel.equals("C5")) _serviceLevel = ServiceLevel.C5;
        if (serviceLevel.equals("DL")) _serviceLevel = ServiceLevel.DL;
        super.setN(8);
    }  

    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param price the price to name
     * @param criticalValue the critical value to set
     * @param keySupplier the supplier key to set
     * @param serviceLevel the service level to set
     * @param stock the stock to set
     */
    Container(String key, int price, int criticalValue, String keySupplier, 
        String serviceType, String serviceLevel, int stock) {
        this(key, price, criticalValue, keySupplier, serviceType, serviceLevel);
        super.setStock(stock);
    }  

    /**
     * @return String Container toString
     */
    @Override
    public String toString() {
        return String.format("CONTAINER|%s|%s", super.toStringExtended(), _serviceLevel);
    }

}
