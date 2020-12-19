package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
import woo.exceptions.*;
import woo.app.exceptions.*;


/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

    private Input<String> supplierKey;
    private Input<String> prodKey;
    private Input<Integer> amount;
    private Input<Boolean> addProd;

  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
    
  }

  @Override
  public final void execute() throws DialogException, UnknownSupplierKeyException,
  UnknownProductKeyException, UnauthorizedSupplierException, WrongSupplierException
    {
      _form.clear();
      supplierKey = _form.addStringInput(Message.requestSupplierKey());
      prodKey = _form.addStringInput(Message.requestProductKey());
      amount = _form.addIntegerInput(Message.requestAmount());
      addProd = _form.addBooleanInput(Message.requestMore());

      _form.parse();
      String temp = supplierKey.value();
    
    try {
      _receiver.registerOrder(temp, prodKey.value(), amount.value());
      while(addProd.value()){
        _form.clear();
        prodKey = _form.addStringInput(Message.requestProductKey());
        amount = _form.addIntegerInput(Message.requestAmount());
        addProd = _form.addBooleanInput(Message.requestMore());
        _form.parse();
        _receiver.addRequestToOrder(temp, prodKey.value(), amount.value());
      }
    }
    catch (NoSuchSupplierKeyException e) {
      throw new UnknownSupplierKeyException(supplierKey.value());
    }
    catch (NoSuchProductKeyException e) {
      throw new UnknownProductKeyException(prodKey.value());
    }
    catch (NotAuthorizedSupplierException e) {
      throw new UnauthorizedSupplierException(supplierKey.value());
    }
    catch (WrongSupplierForProductException e) {
      throw new WrongSupplierException(supplierKey.value(), prodKey.value());
    }

  }

}
