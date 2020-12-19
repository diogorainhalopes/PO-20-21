package woo;

public interface TransactionsVisitor {
    public int visit(Order order);
    public int visit(Sale sale);
}