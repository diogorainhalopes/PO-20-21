package woo;



public class Book extends Product {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Book's title */
    private String _title;

    /** Book's author */
    private String _author;

    /** Book's ISBN */
    private String _ISBN;
  


    /**
     * @return String return the title
     */
    public String getTitle() {
        return _title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        _title = title;
    }

    /**
     * @return String return the author
     */
    public String getAuthor() {
        return _author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        _author = author;
    }

    /**
     * @return String return the ISBN
     */
    public String getISBN() {
        return _ISBN;
    }

    /**
     * @param ISBN the ISBN to set
     */
    public void setISBN(String ISBN) {
        _ISBN = ISBN;
    }



    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param price the price to set
     * @param criticalValue the critical value to set
     * @param keySupplier the supplier key to set
     * @param title the title to set
     * @param author the author to set
     * @param ISBN the ISBN to set
     */
    Book(String key, int price, int criticalValue, String keySupplier, 
        String title, String author, String ISBN) {
        super(key, price, criticalValue, keySupplier);
        _title = title;
        _author = author;
        _ISBN = ISBN;
    }

    /**
     * Note that key is final and thus, constant after
     * initialization.
     * 
     * @param key the key to set
     * @param price the price to set
     * @param criticalValue the critical value to set
     * @param keySupplier the supplier key to set
     * @param title the title to set
     * @param author the author to set
     * @param ISBN the ISBN to set
     * @param stock the stock to set
     */
    Book(String key, int price, int criticalValue, String keySupplier, 
        String title, String author, String ISBN, int stock) {
        this(key, price, criticalValue, keySupplier, title, author, ISBN);
        super.setStock(stock);
        super.setN(3);
    }

    /**
     * @return String Book toString
     */
    @Override
    public String toString() {
        return String.format("BOOK|%s|%s|%s|%s", super.toString(), _title, 
            _author, _ISBN);
    }

}