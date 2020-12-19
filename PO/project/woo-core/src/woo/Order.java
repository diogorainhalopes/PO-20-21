package woo;
import java.util.ArrayList;


public class Order extends Transaction {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** ArrayList of product ids in this transaction */
    ArrayList<String> _products = new ArrayList<String>();
    
    /** ArrayList of demanded amounts for each product in this transaction */
    ArrayList<Integer> _amounts = new ArrayList<Integer>();

    /**
     * 
     * @param supplierKey the supplier's key to set
     * @param prodKey the product's key to set
     * @param amount the amount of products to order
     * @param price the base price to set
     */
    Order(String supplierKey, String prodKey, int amount, int price, int index,
            int date) {
        super(supplierKey, price, index);
        _products.add(prodKey);
        _amounts.add(amount);
        super.setPaymentDate(date);
    }

    /**
     * 
     * @param prodKey the product's key to set
     * @param amount the amount of products to sell
     * @param price the base price to set
     */
    public void addToOrder(String prodKey, int amount, int price) {
        _products.add(prodKey);
        _amounts.add(amount);
        super.addPrice(price);
    }

    public ArrayList<String> getProdList() {
        return _products;
    }

    public ArrayList<Integer> getAmountsList() {
        return _amounts;
    }

    /**
     * String format: 
     * <pre>id|idFornecedor|valor-base|data-pagamento</pre>
     * idProduto|quantidade
     * (...)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String s = String.format("%d|%s|%d|%d", super.getIndex(), 
            super.getIdEntity(), (int) Math.round(super.getBasePrice()), super.getPaymentDate());

        for (String p: _products) {
            int i = _products.indexOf(p);
            s = String.format("%s\n%s|%d", s, p, _amounts.get(i));
        }
        return s;
    }

    public int accept(TransactionsVisitor visitor) {
        return visitor.visit(this);
    }



}





















