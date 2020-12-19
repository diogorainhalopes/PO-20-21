package woo.app.products;

import pt.tecnico.po.ui.Command;              
import pt.tecnico.po.ui.DialogException;     
import pt.tecnico.po.ui.Input;       
import woo.Storefront;
import woo.app.exceptions.*;
import woo.exceptions.*;  
/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {

  private Input<String> id;
  private Input<Integer> price;
  private Input<Integer> criticalValue;
  private Input<String> supplierKey;
  private Input<String> serviceType;
  private Input<String> serviceLevel;

  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    id = _form.addStringInput(Message.requestProductKey());
    price = _form.addIntegerInput(Message.requestPrice());
    criticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    supplierKey = _form.addStringInput(Message.requestSupplierKey());
    serviceType = _form.addStringInput(Message.requestServiceType());
    serviceLevel = _form.addStringInput(Message.requestServiceLevel());
  }

  @Override
  public final void execute() throws DialogException, DuplicateProductKeyException,
  UnknownSupplierKeyException, UnknownServiceTypeException, UnknownServiceLevelException {
    _form.parse();
    try {
      _receiver.registerContainer(id.value(), price.value(), criticalValue.value(), 
        supplierKey.value(), serviceType.value(), serviceLevel.value());
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
    catch (NoSuchServiceLevelException e) {
      throw new UnknownServiceLevelException(serviceLevel.value());
    }
  }
}
