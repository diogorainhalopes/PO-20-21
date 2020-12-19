package woo.app.products;

import pt.tecnico.po.ui.Command;        
import pt.tecnico.po.ui.DialogException;    
import pt.tecnico.po.ui.Input;       
import woo.Storefront;                  
import woo.app.exceptions.*;
import woo.exceptions.*; 
/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {

  private Input<String> id;
  private Input<String> title;
  private Input<String> author;
  private Input<String> ISBN;
  private Input<Integer> price;
  private Input<Integer> criticalValue;
  private Input<String> supplierKey;


  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    id = _form.addStringInput(Message.requestProductKey());
    title = _form.addStringInput(Message.requestBookTitle());
    author = _form.addStringInput(Message.requestBookAuthor());
    ISBN = _form.addStringInput(Message.requestISBN());
    price = _form.addIntegerInput(Message.requestPrice());
    criticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public final void execute() throws DialogException, DuplicateProductKeyException, 
    UnknownSupplierKeyException {
    _form.parse();
    try {
      _receiver.registerBook(id.value(), title.value(),author.value(), ISBN.value(), 
      price.value(), criticalValue.value(), supplierKey.value());
    }
    catch (NonUniqueProductKeyException e) {
      throw new DuplicateProductKeyException(id.value());
    }
    catch (NoSuchSupplierKeyException e) {
      throw new UnknownSupplierKeyException(supplierKey.value());
    }
  }
}
