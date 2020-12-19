package woo.app.products;

import pt.tecnico.po.ui.Command;   
import woo.app.exceptions.*;
import woo.exceptions.*;         
import pt.tecnico.po.ui.DialogException;    
import pt.tecnico.po.ui.Input;    
import woo.Storefront;      

/**
 * Register box.
 */
public class DoRegisterProductBox extends Command<Storefront> {

  private Input<String> id;
  private Input<Integer> price;
  private Input<Integer> criticalValue;
  private Input<String> supplierKey;
  private Input<String> serviceType;

  public DoRegisterProductBox(Storefront receiver) {
    super(Label.REGISTER_BOX, receiver);
    id = _form.addStringInput(Message.requestProductKey());
    price = _form.addIntegerInput(Message.requestPrice());
    criticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    supplierKey = _form.addStringInput(Message.requestSupplierKey());
    serviceType = _form.addStringInput(Message.requestServiceType());
  }

  @Override
  public final void execute() throws DialogException, DuplicateProductKeyException,
    UnknownSupplierKeyException, UnknownServiceTypeException {
    _form.parse();
    try {
      _receiver.registerBox(id.value(), price.value(), criticalValue.value(), 
        supplierKey.value(), serviceType.value());
    }
    catch (NonUniqueProductKeyException e) {
      throw new DuplicateProductKeyException(id.value());
    }
    catch (NoSuchSupplierKeyException e) {
      throw new UnknownSupplierKeyException(supplierKey.value());
    }
    catch (NoSuchServiceTypeException e) {
      throw new UnknownServiceTypeException(serviceType.value());
    }
  }
}
