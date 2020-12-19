package woo;

import java.io.*;
import woo.exceptions.*;




/**
 * Storefront: façade for the core classes.
 */
public class Storefront {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store = new Store();

  /** True if there were modifications */
  private boolean _save = false;



  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    
    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new 
    FileOutputStream(_filename)));
    oos.writeObject(_store);
    oos.close();
    _save = false;
  }


  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * Checks if the filename to save is empty
   */
  public boolean isSaveNameEmpty() {
    if(_filename.equals("")) {
      return true;
    }
    return false;
  }



  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException {
    try { 
    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
    _store = (Store) ois.readObject();
    ois.close(); 
    }
    catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
    _filename = filename;
  }
  

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
    } catch (IOException | BadEntryException | FileErrorException  e) {
      throw new ImportFileException(textfile);
    }
    _save = true;
  }

  /**
   * @param clientKey
   * @param prodKey
   * @throws NoSuchClientKeyException
   * @throws NoSuchProductKeyException
   */
  public void toggleProductNotifications(String clientKey, String prodKey) 
    throws NoSuchClientKeyException, NoSuchProductKeyException {
      _store.toggleProductNotifications(clientKey, prodKey);
      _save = true;
  }





  /**
   * @param key
   * @param name
   * @param address
   * @throws NonUniqueClientKeyException
   */
  public void registerClient(String key, String name, String address) throws 
    NonUniqueClientKeyException {

    _store.registerClient(key, name, address);
    _save = true;
  }


  /**
   * @param id
   * @param name
   * @param address
   * @throws NonUniqueSupplierKeyException
   */
  public void registerSupplier(String key, String name, String address) throws 
    NonUniqueSupplierKeyException {
      _store.registerSupplier(key, name, address);
      _save = true;
    }

  /**
   * @param id
   * @param name
   * @param address
   * @throws NoSuchSupplierKeyException
   */
  public boolean toggleTransactions(String key) throws NoSuchSupplierKeyException {
    _save = true;
    return _store.toggleTransactions(key);

  }


  /**
   * @param clientKey
   * @param paymentDeadline
   * @param prodKey
   * @param amount
   * @throws NoSuchClientKeyException
   * @throws NoSuchProductKeyException 
   * @throws UnavailableProductAmountException
   */
  public void registerSale(String clientKey, int paymentDeadline, 
    String prodKey, int amount) throws NoSuchClientKeyException,
      NoSuchProductKeyException, UnavailableProductAmountException {

    _store.registerSale(clientKey, paymentDeadline, prodKey, amount);
    _save = true;
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
  public void registerOrder(String supplierKey, String prodKey, 
    int amount) throws NoSuchSupplierKeyException, NoSuchProductKeyException, 
      NotAuthorizedSupplierException, WrongSupplierForProductException {

    _store.registerOrder(supplierKey, prodKey, amount);
    _save = true;
  }

  /**
   * @param supplierKey
   * @param prodKey
   * @param amount
   * @throws NoSuchProductKeyException 
   * @throws WrongSupplierForProductException
   */
  public void addRequestToOrder(String supplierKey, String prodKey, int amount) 
    throws NoSuchProductKeyException, WrongSupplierForProductException {

    _store.addRequestToOrder(supplierKey, prodKey, amount);
    _save = true;
  }

  /**
   * @param transKey
   * @throws NoSuchTransactionKeyException
   */
  public String showTransaction(int transKey) throws NoSuchTransactionKeyException {
    return _store.showTransaction(transKey);
  }

  /**
   * @param id
   * @throws NoSuchSupplierKeyException 
   */
  public String showSupplierTransactions(String key) throws NoSuchSupplierKeyException {
    return _store.showSupplierTransactions(key);
  }

  
  /**
   * @param id
   * @throws NoSuchClientKeyException 
   */
  public String showClientTransactions(String key) throws NoSuchClientKeyException {
    return _store.showClientTransactions(key);
  }



  /**
   * @param saleKey
   * @throws NoSuchTransactionKeyException
   */
  public void paySale(int saleKey) throws NoSuchTransactionKeyException {
    _store.paySale(saleKey);
    _save = true;
  }
  /**
   * @param key
   * @throws NoSuchClientKeyException
   */
  public String showClient(String key) throws NoSuchClientKeyException {
    return _store.showClient(key);
  }


  /**
   * Lists all clients currently registered.
   */
  public String showAllClients() {
    return _store.showAllClients();
  }


  /**
   * Lists all suppliers currently registered.
   */
  public String showAllSuppliers() {
    return _store.showAllSuppliers();
  }


  /**
  * @return int available balance at the current date
  */
  public int showAvailableBalance() {
    return _store.showAvailableBalance();
  }

  /**
  * @return int accounting balance at the current date
  */
  public int showAccountingBalance() {
    return _store.showAccountingBalance();
  }

  /**
   * Lists all paid transactions of a given client
   * @return String
   * @throws NoSuchClientKeyException
   */
  public String showPaidTransactions(String key) throws NoSuchClientKeyException {
    return _store.showPaidTransactions(key);
  }


  /**
   * Lists all paid transactions of a given client
   * @return String
   */
  public String showBelowLimitProducts(int limit) {
    return _store.showBelowLimitProducts(limit);
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
        _store.registerBox(key, price, criticalValue, supplierKey, serviceType);
        _save = true;
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
        _store.registerContainer(key, price, criticalValue, supplierKey, serviceType, serviceLevel);
        _save = true;
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
        _store.registerBook(key, title, author, ISBN, price, criticalValue, supplierKey);
        _save = true;
  }

  /** 
   * @param key
   * @param price
   * @throws NoSuchProductKeyException
   */
  public void changePrice(String prodKey, int newPrice) throws NoSuchProductKeyException {
    _store.changePrice(prodKey, newPrice);
    _save = true;
  }

  /**
    * @return int return the date
    */
  public int getDate() {
    return _store.getDate();
  }

  
  /**
   * @param _date the date to set
   */
  public int advanceDate(int date) throws BadDateException {
    _save = true;  
    return _store.advanceDate(date);
  }


  /**
   * Lists all products currently registered.
   */
  public String showAllProducts() {
    return _store.showAllProducts();
  }


  /**
   * @param _save True if there were modifications
   */
  public boolean getSave() {
    return _save;
  }


  /**
   * @return _filename
   */
  public String getFileName() {
    return _filename;
  }


  /**
   * @param filename the filename too set
   */
  public void setFileName(String filename) {
    _filename = filename;
  }

}