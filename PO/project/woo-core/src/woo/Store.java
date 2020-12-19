package woo;

import java.io.*;
import java.util.*;

import woo.exceptions.*;
import java.util.regex.*;


/**
 * Class Store implements a store.
 */
public class Store implements Serializable, TransactionsVisitor {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;


  /** Clients TreeMap */
  private Map<String, Client> _clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER) ;

  /** Suppliers TreeMap */
  private Map<String, Supplier> _suppliers = new TreeMap<String, Supplier>(String.CASE_INSENSITIVE_ORDER);

  /** Products TreeMap */
  private Map<String, Product> _products = new TreeMap<String, Product>(String.CASE_INSENSITIVE_ORDER);

  /** Transactions TreeMap */
  private Map<Integer, Transaction> _transactions = new TreeMap<Integer, Transaction>();

  private final String notifStock = "NEW";
  private final String notifPrice = "BARGAIN";
  private final int isSale = 0;
  private final int isOrder = 1;


  /** Current date */
  private int _date = 0;

  /** Order index */
  private int _indexG = 0;


  /**
   * @return void
   */
  public Store() {}



  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException, 
          FileErrorException  {
    
    BufferedReader reader = new BufferedReader(new FileReader(txtfile));
    String line;
    try{
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split("\\|");
            Pattern patClient = Pattern.compile("^(CLIENT)");
            Pattern patSupplier = Pattern.compile("^(SUPPLIER)");
            Pattern patBox = Pattern.compile("^(BOX)");
            Pattern patContainer = Pattern.compile("^(CONTAINER)");
            Pattern patBook = Pattern.compile("^(BOOK)");
            if (patClient.matcher(fields[0]).matches()){
              Client client = new Client(fields[1], fields[2], fields[3]);
              for(Map.Entry<String, Product> p: _products.entrySet()) { 
                Observer o = (Observer) client;
                p.getValue().registerObserver(o);
              } 
              _clients.put(fields[1], client);
            }
            else if(patSupplier.matcher(fields[0]).matches()){
              Supplier supplier  = new Supplier(fields[1], fields[2], fields[3]);
              _suppliers.put(fields[1], supplier);
            }
            else if(patBox.matcher(fields[0]).matches()){
              int price = Integer.parseInt(fields[4]);
              int v_crit = Integer.parseInt(fields[5]);
              int stock = Integer.parseInt(fields[6]);
              Product box = new Box(fields[1], price, v_crit,  fields[3],
                fields[2], stock);
              for(Map.Entry<String, Client> c: _clients.entrySet()) { 
                Observer o = (Observer) c.getValue();
                box.registerObserver(o);
              }
              _products.put(fields[1], box);
            }
            else if(patContainer.matcher(fields[0]).matches()){
              int price = Integer.parseInt(fields[5]);
              int v_crit = Integer.parseInt(fields[6]);
              int stock = Integer.parseInt(fields[7]);
              Product container = new Container(fields[1], price, v_crit,  fields[4],
                fields[2], fields[3], stock);
              for(Map.Entry<String, Client> c: _clients.entrySet()) { 
                Observer o = (Observer) c.getValue();
                container.registerObserver(o);
              }
              _products.put(fields[1], container);
            }
            else if(patBook.matcher(fields[0]).matches()){
              int price = Integer.parseInt(fields[6]);
              int v_crit = Integer.parseInt(fields[7]);
              int stock = Integer.parseInt(fields[8]);
              Product book = new Book(fields[1], price, v_crit, fields[5], 
                  fields[2], fields[3], fields[4], stock);
              for(Map.Entry<String, Client> c: _clients.entrySet()) { 
                Observer o = (Observer) c.getValue();
                book.registerObserver(o);
              }
              _products.put(fields[1], book);
            }
            else {
                throw new BadEntryException(fields[0]);
            }
        }
        
      }
      catch(IOException e) {
          throw new FileErrorException(txtfile);
      }
      finally {
        reader.close();
      }
  }


  /**
   * @param id
   * @param name
   * @param address
   * @throws NonUniqueClientKeyException
   */
  public void registerClient(String key, String name, String address) throws 
    NonUniqueClientKeyException {
      if(searchClientKey(key))
          throw new NonUniqueClientKeyException(key);
      Client c = new Client(key, name, address);
      
      for(Map.Entry<String, Product> p: _products.entrySet()) { 
        Observer o = (Observer) c;
        p.getValue().registerObserver(o);
      } 
      _clients.put(key, c);
  }


  /**
   * @param id
   * @throws NonUniqueClientKeyException
   */
  public String showClient(String key) throws NoSuchClientKeyException {
      if(!searchClientKey(key))
          throw new NoSuchClientKeyException(key);
      String str = _clients.get(key).toString() + _clients.get(key).display();
      _clients.get(key).getNotifications().clear();
      return str;
  }

  /**
   * @param clientKey
   * @param prodKey
   * @throws NoSuchClientKeyException
   * @throws NoSuchProductKeyException
   */
  public void toggleProductNotifications(String clientKey, String prodKey) 
    throws NoSuchClientKeyException, NoSuchProductKeyException {
      if(!searchClientKey(clientKey))
          throw new NoSuchClientKeyException(clientKey);
      if(!searchProductKey(prodKey))
          throw new NoSuchProductKeyException(prodKey);
      Observer o = (Observer) _clients.get(clientKey);
      Product p = _products.get(prodKey);
      if (p.searchObserver(o)) {
        p.removeObserver(o);
      }
      else {
        p.registerObserver(o);
      }
  }


  /**
   * Lists all clients currently registered.
   * @return String
   */
  public String showAllClients() {
    String str = "";

    for(Map.Entry<String, Client> c: _clients.entrySet())
      str = str + c.getValue().toString()+ "\n";

    return str;
  }


  /**
   * Checks if the key is mapped.
   * @param key
   * @return boolean
   */
  public boolean searchClientKey(String key) {
    return _clients.containsKey(key);
  }

  
  /**
   * Checks if the key is mapped.
   * @param key
   * @return boolean
   */
  public boolean searchSupplierKey(String key) {
    return _suppliers.containsKey(key);
  }

  
  /**
   * Checks if the key is mapped.
   * @param key
   * @return boolean
   */
  public boolean searchProductKey(String key) {
    return _products.containsKey(key);
  }

  /**
   * Lists all suppliers currently registered.
   */
  public String showAllSuppliers() {
    String str = "";

    for(Map.Entry<String, Supplier> s: _suppliers.entrySet())
      str += s.getValue().toString()+ "\n";

    return str;
  }


  /**
   * Lists all products currently registered.
   */
  public String showAllProducts() {
    String str = "";

    for(Map.Entry<String, Product> p: _products.entrySet())
      str += p.getValue().toString()+ "\n";

    return str;
  }


  /**
    * @return int return the date
    */
  public int getDate() {
    return _date;
  }
  

  /**
   * @param date the date to set
   */
  public void setDate(int date) {
      _date = date;
  }

  
  /**
    * @return int return the globalindex
    */
    public int getIndex() {
      return _indexG;
    }
    
  
    /**
     * @param index the global index to set
     *
    public void setIndex(int index) {
        _indexG = index;
    }*/
  
    /**
    * @param index the global index to set
    */
   public void incIndex() {
       _indexG++;
   }


  /**
   * @param _date the date to set
   */
  public int advanceDate(int date) throws BadDateException {
    if (date > 0)
      return _date += date;
    else
      throw new BadDateException(date);
  }




  /**
   * @param supplierKey 
   * @param prodKey
   * @param amount
   * @throws NoSuchSupplierKeyException
   * @throws NoSuchProductKeyException  
   * @throws NotAuthorizedSupplierException
   * @throws WrongSupplierForProductException
   */
  public void registerOrder(String supplierKey, String prodKey, int amount) throws 
    NoSuchSupplierKeyException, NoSuchProductKeyException, NotAuthorizedSupplierException,
     WrongSupplierForProductException {

      if(!searchSupplierKey(supplierKey))
          throw new NoSuchSupplierKeyException(supplierKey);
      if(!searchProductKey(prodKey))
          throw new NoSuchProductKeyException(prodKey);
      Supplier s = _suppliers.get(supplierKey);
      if (!s.getTrade())
          throw new  NotAuthorizedSupplierException(supplierKey);
      Product p = _products.get(prodKey);
      if(!p.getKeySupplier().equals(supplierKey))
          throw new WrongSupplierForProductException(supplierKey, prodKey);

      int total_price = amount * p.getPrice();
       
      Transaction t = new Order(supplierKey, prodKey, amount, total_price, 
          _indexG, getDate());
      if(p.getStock() == 0) {
        p.notifyObservers(notifStock);
      }
      p.addStock(amount);
      _transactions.put(_indexG, t);
      incIndex();
  }

  /**
   * @param supplierKey 
   * @param prodKey
   * @param amount
   * @throws WrongSupplierForProductException
   * @throws NoSuchProductKeyException  
   */
  public void addRequestToOrder(String supplierKey, String prodKey, int amount) throws 
   NoSuchProductKeyException, WrongSupplierForProductException {
    
    if(!searchProductKey(prodKey)) {
        _transactions.remove(--_indexG);
        throw new NoSuchProductKeyException(prodKey);
    }

    Product p = _products.get(prodKey);
    if(!p.getKeySupplier().equals(supplierKey)) {
        _transactions.remove(--_indexG);
        throw new WrongSupplierForProductException(supplierKey, prodKey);
    }

    int total_price = amount * p.getPrice();
    Order o = (Order) _transactions.get(getIndex()-1);
    ArrayList<String> prods = o.getProdList();
    ArrayList<Integer> amounts = o.getAmountsList();

    if(prods.contains(prodKey)) {
      int ind = prods.indexOf(prodKey);
      int amt = amounts.get(ind);
      amt += amount;
      amounts.set(ind, amt);
    }
    else {
      o.addToOrder(prodKey, amount, total_price);
    }

    if(p.getStock() == 0) {
      p.notifyObservers(notifStock);
    }
    p.addStock(amount);
}




  /**
   * @param clientKey
   * @param paymentDeadline
   * @param prodKey
   * @param amount
   * @throws NonUniqueClientKeyException
   * @throws NoSuchClientKeyException
   * @throws UnavailableProductException
   */
  public void registerSale(String clientKey, int paymentDeadline,
    String prodKey, int amount) throws NoSuchClientKeyException,
      NoSuchProductKeyException, UnavailableProductAmountException {

    if(!searchClientKey(clientKey))
        throw new NoSuchClientKeyException(clientKey);
    if(!searchProductKey(prodKey))
        throw new NoSuchProductKeyException(prodKey);

    Product p = _products.get(prodKey);

    if(p.getStock() < amount)
        throw new UnavailableProductAmountException(prodKey, amount, p.getStock());

    p.addStock(-amount);
    int initial_price = amount * p.getPrice();
    int N = p.getN();
    Transaction t = new Sale(clientKey, paymentDeadline, prodKey, amount, 
        initial_price, N, _indexG);
    _transactions.put(_indexG, t);
    incIndex();
    Client c = _clients.get(clientKey);
    c.addPurchasesValue(initial_price);
  }

  /**
   * @param transKey
   * @throws NoSuchTransactionKeyException
   */
  public String showTransaction(int transKey) throws NoSuchTransactionKeyException {

    if ((transKey < 0) || (transKey >= _indexG))
      throw new NoSuchTransactionKeyException(transKey);

    Transaction t = _transactions.get(transKey);
    if(t.accept(this) == isOrder) {
      Order o = (Order) t;
      return o.toString();
    }
    else {
      Sale s = (Sale) t;
      Client c = _clients.get(s.getIdEntity());
      s.setPriceToPay(c.getStatus(), _date);
      return s.toString();
    }
  }



  /**
   * @param saleKey
   * @throws NoSuchTransactionKeyException
   */
  public void paySale(int saleKey) throws NoSuchTransactionKeyException {

    if ((saleKey < 0) || (saleKey >= _indexG))
      throw new NoSuchTransactionKeyException(saleKey);
    Transaction t = _transactions.get(saleKey);
    if(t.accept(this) == isOrder) {
      return;
    }
    Sale s = (Sale) t;
    if (s.getPaymentDate() != s.getStarterPaymentDate()) {
      return;
    }
    Product p = _products.get(s.getProdKey());
    String clientKey = s.getIdEntity();
    Client c = _clients.get(clientKey);
    s.setPriceToPay(c.getStatus(), getDate());
    c.addPurchasesPaid(s.getPriceToPay());
    c.updateStatus(s.getPriceToPay(), (s.getPaymentDeadline() - getDate()));
    p.addStock(-(s.getAmount()));
    s.setPaymentDate(getDate());
  }



  /**
   * @param id
   * @param name
   * @param address
   * @throws NonUniqueSupplierKeyException
   */
  public void registerSupplier(String key, String name, String address) throws 
    NonUniqueSupplierKeyException {
      if(searchSupplierKey(key))
          throw new NonUniqueSupplierKeyException(key);
      Supplier s = new Supplier(key, name, address);
      _suppliers.put(key, s);
  }

  /**
   * @param id
   * @param name
   * @param address
   * @throws NoSuchSupplierKeyException
   */
  public boolean toggleTransactions(String key) throws NoSuchSupplierKeyException {

    if(!searchSupplierKey(key))
          throw new NoSuchSupplierKeyException(key);
    Supplier s = _suppliers.get(key);
    s.toggleTrade();
    return s.getTrade();
  }

  /**
   * @param id
   * @throws NoSuchSupplierKeyException 
   */
  public String showSupplierTransactions(String key) throws NoSuchSupplierKeyException {
    if(!searchSupplierKey(key))
          throw new NoSuchSupplierKeyException(key);
    String str = "";

    for(Map.Entry<Integer, Transaction> t : _transactions.entrySet()) {
      if(t.getValue().accept(this) == isOrder) {
        if(t.getValue().getIdEntity().equals(key)) {
          Order o = (Order) t.getValue();
          str += o.toString() + "\n";
        }
      } 
    }
    return str;
  }




  /**
   * @param id
   * @throws NoSuchClientKeyException 
   */
  public String showClientTransactions(String key) throws NoSuchClientKeyException {
    String str = "";
    if(!searchClientKey(key))
          throw new NoSuchClientKeyException(key);
    for(Map.Entry<Integer, Transaction> t : _transactions.entrySet()) {
      if(t.getValue().accept(this) == isSale) {
        if(t.getValue().getIdEntity().equals(key)) {
          Sale s = (Sale) t.getValue();
          str += s.toString() + "\n";
        }
      } 
    }
    return str;
  }


  /**
   * @param key
   * @param price
   * @param criticalValue
   * @param supplierKey
   * @param serviceType
   * @throws NonUniqueProductKeyException
   * @throws NoSuchSupplierKeyException
   * @throws NoSuchServiceTypeException
   */
  public void registerBox(String key, int price, int criticalValue, String supplierKey,
    String serviceType) throws NonUniqueProductKeyException, NoSuchSupplierKeyException, 
      NoSuchServiceTypeException {
      if(searchProductKey(key))
          throw new NonUniqueProductKeyException(key);
      if(!searchSupplierKey(supplierKey))
          throw new NoSuchSupplierKeyException(supplierKey);
      if (!(serviceType.equals("NORMAL") || serviceType.equals("AIR") 
        || serviceType.equals("EXPRESS") || serviceType.equals("PERSONAL"))) 
          throw new NoSuchServiceTypeException(serviceType);
      
      Product p = new Box(key, price, criticalValue, supplierKey, serviceType);
      for(Map.Entry<String, Client> c: _clients.entrySet()) { 
          Observer o = (Observer) c.getValue();
          p.registerObserver(o);
      }
      _products.put(key, p);
  }

  /** 
   * @param key
   * @param price
   * @param criticalValue
   * @param supplierKey
   * @param serviceType
   * @throws NonUniqueProductKeyException
   * @throws NoSuchSupplierKeyException
   * @throws NoSuchServiceTypeException
   * @throws NoSuchServiceLevelException
   */
  public void registerContainer(String key, int price, int criticalValue, String supplierKey,
    String serviceType, String serviceLevel) throws NonUniqueProductKeyException, 
      NoSuchSupplierKeyException, NoSuchServiceTypeException, NoSuchServiceLevelException {
      if(searchProductKey(key))
          throw new NonUniqueProductKeyException(key);
      if(!searchSupplierKey(supplierKey))
          throw new NoSuchSupplierKeyException(supplierKey);
      if (!(serviceType.equals("NORMAL") || serviceType.equals("AIR") 
        || serviceType.equals("EXPRESS") || serviceType.equals("PERSONAL"))) 
          throw new NoSuchServiceTypeException(serviceType);

      if (!(serviceLevel.equals("B4") || serviceLevel.equals("C4") 
        || serviceLevel.equals("C5") || serviceLevel.equals("DL")))
          throw new NoSuchServiceLevelException(serviceLevel);
      
      Product p = new Container(key, price, criticalValue, supplierKey, serviceType, serviceLevel);
      for(Map.Entry<String, Client> c: _clients.entrySet()) { 
        Observer o = (Observer) c.getValue();
        p.registerObserver(o);
      }
      _products.put(key, p);
  }


  /** 
   * @param key
   * @param price
   * @param criticalValue
   * @param supplierKey
   * @param serviceType
   * @throws NonUniqueProductKeyException
   * @throws NoSuchSupplierKeyException
   */
  public void registerBook(String key, String title, String author, String ISBN,
    int price, int criticalValue, String supplierKey) throws NonUniqueProductKeyException, 
      NoSuchSupplierKeyException  {
      if(searchProductKey(key))
          throw new NonUniqueProductKeyException(key);
      if(!searchSupplierKey(supplierKey))
          throw new NoSuchSupplierKeyException(supplierKey);
      
      Product p = new Book(key, price, criticalValue, supplierKey, title, author, ISBN);
      for(Map.Entry<String, Client> c: _clients.entrySet()) { 
        Observer o = (Observer) c.getValue();
        p.registerObserver(o);
      }
      _products.put(key, p);
  }

  /** 
   * @param key
   * @param price
   * @throws NoSuchProductKeyException
   */
  public void changePrice(String prodKey, int newPrice) throws NoSuchProductKeyException {
    if(!searchProductKey(prodKey))
        throw new NoSuchProductKeyException(prodKey);
    if(newPrice <= 0) 
        return;
    Product p = _products.get(prodKey);
    if(p.getPrice() > newPrice) {
      p.setPrice(newPrice);
      p.notifyObservers(notifPrice);
      return;
    }
    p.setPrice(newPrice);
  }

  /**
  * @return int available balance at the current date
  */
  public int showAvailableBalance() {
    double avalBalance = 0;
    for(Map.Entry<Integer, Transaction> t : _transactions.entrySet()) {
      if(t.getValue().accept(this) == isOrder) {
        Order o = (Order) t.getValue();
        avalBalance -= o.getBasePrice();
      }
      else {
        Sale s = (Sale) t.getValue();
        if(s.getPaymentDate() != s.getStarterPaymentDate()) {
          avalBalance += s.getPriceToPay();
        }
      }
    }
    return (int) Math.round(avalBalance);
  }

  /**
  * @return int accounting balance at the current date
  */
  public int showAccountingBalance() {
    double avalBalance = 0;
    for(Map.Entry<Integer, Transaction> t : _transactions.entrySet()) {
      if(t.getValue().accept(this) == isOrder) {
        Order o = (Order) t.getValue();
        avalBalance -= o.getBasePrice();
      }
      else {
        Sale s = (Sale) t;
        Client c = _clients.get(s.getIdEntity());
        s.setPriceToPay(c.getStatus(), _date);
        avalBalance += s.getPriceToPay();
      }
    }
    return (int) Math.round(avalBalance);
  }


  /**
   * Lists all paid transactions of a given client
   * @return String
   * @throws NoSuchClientKeyException
   */
  public String showPaidTransactions(String key) throws NoSuchClientKeyException {
    String str = "";
    if(!searchClientKey(key))
          throw new NoSuchClientKeyException(key);
    
    for(Map.Entry<Integer, Transaction> t : _transactions.entrySet()) {
      if(t.getValue().accept(this) == isSale) {
        if(t.getValue().getIdEntity().equals(key)) {
          Sale s = (Sale) t.getValue();
          if(s.getPaymentDate() != s.getStarterPaymentDate()) {
            str += s.toString() + "\n";
          }
        }
      }
    }
    return str;
  }
  

  /**
   * Lists all paid transactions of a given client
   * @return String
   */
  public String showBelowLimitProducts(int limit) {
    String str = "";
        
    for(Map.Entry<String, Product> p: _products.entrySet()) {        
        if(p.getValue().getPrice() < limit) {
          str += p.getValue().toString() + "\n";
        }
    }
    return str;
  }



  public int visit(Order order) {
    return isOrder;
  }

  public int visit(Sale sale) {
    return isSale;
  }



}


