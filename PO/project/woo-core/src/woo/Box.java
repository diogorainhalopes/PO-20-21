package woo;

public class Box extends Product {
    
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Box service type enumeration */
    public enum ServiceType{
        NORMAL,
        AIR,
        EXPRESS,
        PERSONAL
    };
    
    /** Box's service type */
    private ServiceType _serviceType;
    


    /**
     * @return String return the serviceLevel
     */
    public ServiceType getServiceType() {
        return _serviceType;
    }

   
    
    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param price the price to set
     * @param criticalValue the critical value to set
     * @param keySupplier the supplier key to set
     * @param serviceType the service level to set
     */
    Box(String key, int price, int criticalValue, String keySupplier, 
        String serviceType) {
        super(key, price, criticalValue, keySupplier);
        if (serviceType.equals("NORMAL")) _serviceType = ServiceType.NORMAL;
        if (serviceType.equals("AIR")) _serviceType = ServiceType.AIR;
        if (serviceType.equals("EXPRESS")) _serviceType = ServiceType.EXPRESS;
        if (serviceType.equals("PERSONAL")) _serviceType = ServiceType.PERSONAL;
        super.setN(5);
    }  

    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param price the price to set
     * @param criticalValue the critical value to set
     * @param keySupplier the supplier key to set
     * @param serviceType the service level to set
     * @param stock the stock to set
     */
    Box(String key, int price, int criticalValue, String keySupplier, 
        String serviceType, int stock) {
        this(key, price, criticalValue, keySupplier, serviceType);
        super.setStock(stock);
  
    }  

    /**
     * @return String Box toString
     */
    @Override
    public String toString() {
        return String.format("BOX|%s|%s", super.toString(), _serviceType);
    }

    /**
     * @return String Box toString for container (subclass)
     */
    public String toStringExtended() {
        return String.format("%s|%s", super.toString(), _serviceType);

    }

}