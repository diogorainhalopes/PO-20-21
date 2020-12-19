package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
import woo.exceptions.*;
import woo.app.exceptions.*;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {

    private Input<String> clientKey;
    private Input<Integer> paymentDeadline;
    private Input<String> prodKey;
    private Input<Integer> amount;


  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    clientKey = _form.addStringInput(Message.requestClientKey());
    paymentDeadline = _form.addIntegerInput(Message.requestPaymentDeadline());
    prodKey = _form.addStringInput(Message.requestProductKey());
    amount = _form.addIntegerInput(Message.requestAmount());
  }

  @Override
  public final void execute() throws DialogException, UnavailableProductException,
    UnknownClientKeyException, UnknownProductKeyException {
      
    _form.parse();
    try {
      _receiver.registerSale(clientKey.value(), paymentDeadline.value(), 
          prodKey.value(), amount.value());
        
    }
    catch (UnavailableProductAmountException e) {
        throw new UnavailableProductException(prodKey.value(), amount.value(), e.getAvailable());
    }
    catch (NoSuchClientKeyException e) {
      throw new UnknownClientKeyException(clientKey.value());
    }
    catch (NoSuchProductKeyException e) {
      throw new UnknownProductKeyException(prodKey.value());
    }



  }
}
