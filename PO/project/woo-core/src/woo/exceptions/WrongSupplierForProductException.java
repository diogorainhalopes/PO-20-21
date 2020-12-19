package woo.exceptions;


/** Exception for reporting wrong supplier/product associations. */
public class WrongSupplierForProductException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009200054L;

  /** Supplier key. */
  private String _skey;

  /** Product key. */
  private String _pkey;

  /** 
   * @param skey supplier key.
   * @param pkey product key. 
   */
  public WrongSupplierForProductException(String skey, String pkey) {
    _skey = skey;
    _pkey = pkey;
  }

  /** @return supplier key */
  public String getSupplierKey() {
    return _skey;
  } 
  /** @return product key */
  public String getProductKey() {
    return _pkey;
  } 
}
