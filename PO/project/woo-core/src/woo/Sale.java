package woo;



public class Sale extends Transaction {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Copy of product's time limit to avoid penalty or get discount  */
    private int _N;

    /** Product's payment deadline */
    private int _paymentDeadline;

    /** Product's key in this sale */
    private String _prodKey;

    /** Product's amount in this sale */
    private int _amount;

    
    /** Price to pay at current date */
    private double _priceToPay;

    /** Possible time periods for payments  */
    public enum TimePeriod{
        P1,
        P2,
        P3,
        P4
    };

    /** Start payment day (for verification purposes) */
    private final int StarterPaymentDate = -1;

    /** Discounts */
    private final double P1_Discounts = (1-0.1);
    private final double P2_Discounts_S = (1-0.05);
    private final double P2_Discounts_E = (1-0.1);
    private final double P3_Discounts_E = (1-0.05);

    /** Penalty */
    private final double P3_Penalty_N = 0.05;
    private final double P3_Penalty_S = 0.02;
    private final double P4_Penalty_N = 0.1;
    private final double P4_Penalty_S = 0.05;

    /** Timers for price adjustments for SELECTION clients */
    private final int P2_Timer = -2;
    private final int P3_Timer = 1;



    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param clientKey the client's key to set
     * @param paymentDeadline the payment's deadline for this sale to set
     * @param prodKey the product's key to set
     * @param amount the amount of products to sell
     * @param initial_price the base price to set
     * @param N the time period to avoid penalty or get a discount
     */
    public Sale(String clientKey, int paymentDeadline, String prodKey, int amount, 
        int initialPrice, int N, int index) {
        super(clientKey, initialPrice, index);
        super.setPaymentDate(StarterPaymentDate);
        _paymentDeadline = paymentDeadline;
        _prodKey = prodKey;
        _amount = amount;
        _N = N;
    }

    /**
     * @return int return the N
     */
    public int getN() {
        return _N;
    }

    /**
     * @param N the N to set
     *
    public void setN(int N) {
        _N = N;
    } */

    /**
     * @return int return the paymentDeadline
     */
    public int getPaymentDeadline() {
        return _paymentDeadline;
    }

    /**
     * @param paymentDeadline the paymentDeadline to set
     *
    public void setPaymentDeadline(int paymentDeadline) {
        _paymentDeadline = paymentDeadline;
    }*/

     /**
     * @return String return the product's key
     */
    public String getProdKey() {
        return _prodKey;
    }

    /**
     * @param prodKey the product's key to set
     *
    public void setProdKey(String prodKey) {
        _prodKey = prodKey;
    } */

    /**
     * @return int return the product's amount in this sale
     */
    public int getAmount() {
        return _amount;
    }

    /**
     * @return int return the starter constant
     */
    public int getStarterPaymentDate() {
        return StarterPaymentDate;
    }

    /**
     * @return int return the product's amount in this sale
     */
    public double getPriceToPay() {
        return _priceToPay;
    }

    /**
     * @param amount the product's amount in this sale to set
     *
    public void setAmount(int amount) {
        _amount = amount;
    } */


    /**
     * @return Time period according to current date
     */
    public TimePeriod getTimePeriod(int currentDate) {
        if(_paymentDeadline - currentDate >= _N) { return TimePeriod.P1;}

        else if((0 <= (_paymentDeadline - currentDate)) && 
            ((_paymentDeadline - currentDate) < _N)) { return TimePeriod.P2;}

        else if((0 < (currentDate - _paymentDeadline)) && 
            ((currentDate - _paymentDeadline) <= _N)) { return TimePeriod.P3;}
            
        else { return TimePeriod.P4;} // _currentDate - _paymentDeadline > _N
    }



    /**
     * Note that if the payment was made, the price to pay doesn't change. 
     * @param status the current client's status
     * @param currentDate the current date
     */
    public void setPriceToPay(String status, int currentDate) {

        if (super.getPaymentDate() != StarterPaymentDate) {
            return;
        }

        String timeP = String.format("%s", getTimePeriod(currentDate));
        int daysAfterLimit = currentDate - _paymentDeadline;
        
        if(timeP.equals("P1")) { _priceToPay = (super.getBasePrice() * P1_Discounts);}

        else if(status.equals("NORMAL")) {

            if(timeP.equals("P2")) { _priceToPay = super.getBasePrice(); }

            else if(timeP.equals("P3")) { _priceToPay = (super.getBasePrice() + 
                (super.getBasePrice() * (P3_Penalty_N * daysAfterLimit ))); }
            
            else { _priceToPay =  (super.getBasePrice() + 
                (super.getBasePrice() * (P4_Penalty_N * daysAfterLimit))); }
        }
        else if(status.equals("SELECTION")) {

            if(timeP.equals("P2")) { 
                if(daysAfterLimit <= P2_Timer) { _priceToPay =  (super.getBasePrice() * P2_Discounts_S);}

                else { _priceToPay =  super.getBasePrice();}
            }

            else if(timeP.equals("P3")) { 
                if(daysAfterLimit <= P3_Timer) { _priceToPay =  super.getBasePrice();}

                else { _priceToPay =  (super.getBasePrice() + 
                    super.getBasePrice() * (P3_Penalty_S * daysAfterLimit));}
            }
            else { _priceToPay =  (super.getBasePrice() + 
                (super.getBasePrice() * (P4_Penalty_S * daysAfterLimit))); }
        }
        else {  // ELITE
            if(timeP.equals("P2")) {_priceToPay =  (super.getBasePrice() * P2_Discounts_E);}

            else if(timeP.equals("P3")) {_priceToPay =  (super.getBasePrice() * P3_Discounts_E);}

            else { _priceToPay =  super.getBasePrice(); }
        }

    }

    public int accept(TransactionsVisitor visitor) {
        return visitor.visit(this);
    }


    /**
     * String format: 
     * id|idCliente|idProduto|quantidade|valor-base|valor-a-pagamento|data-limite|data-pagamento  
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String s = String.format("%d|%s|%s|%d|%d|%d|%d", super.getIndex(), 
            super.getIdEntity(), _prodKey, _amount, (int) Math.round(super.getBasePrice()), 
                (int) Math.round(_priceToPay), _paymentDeadline);

        if (super.getPaymentDate() == StarterPaymentDate) { return s; }

        else { return String.format("%s|%d", s, super.getPaymentDate());}
    }

    
}

