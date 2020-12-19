package woo;
import java.io.Serializable;

public abstract class Transaction implements Serializable{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Transaction holder's id */
    private String _idEntity;

    /** Total price */
    private double _basePrice;

    /** Index of transaction in the store */
    private int _index;

    /** Payment date */
    private int _paymentDate;


    /**
     * @return String return the idEntity
     */
    public String getIdEntity() {
        return _idEntity;
    }

    /**
     * @param idEntity the idEntity to set
     *
    public void setIdEntity(String idEntity) {
        _idEntity = idEntity;
    }*/

    /**
     * @return double return the price
     */
    public double getBasePrice() {
        return _basePrice;
    }

    /**
     * @param _price the price to set
     *
    public void setPrice(int price) {
        _basePrice = price;
    }*/

    /**
     * @param _price the price to add
     */
    public void addPrice(int price) {
        _basePrice += price;
    }
    

    /**
     * @return int return the index 
     */
    public int getIndex() {
        return _index;
    }

    /**
     * @param index the index to set
     *
    public void setIndex(int index) {
        _index = index;
    }*/

    /**
     * @return int return the payment date 
     */
    public int getPaymentDate() {
        return _paymentDate;
    }

    /**
     * @param paymentDate the date when the payment was done
     */
    protected void setPaymentDate(int paymentDate) {
        _paymentDate = paymentDate;
    }

    
    /**
     * @param idEntity the client/supplier id
     * @param price the base price od the transaction to set
     */
    Transaction(String idEntity, int price, int index) {
        _idEntity = idEntity;
        _basePrice = price;
        _index = index;

    }

    public abstract int accept(TransactionsVisitor visitor);

}













