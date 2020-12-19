package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
import woo.exceptions.*;
import woo.app.exceptions.*;

/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<Storefront> {

  private Input<Integer> key;

  
  public DoPay(Storefront storefront) {
    super(Label.PAY, storefront);
    key = _form.addIntegerInput(Message.requestTransactionKey());

  }

  @Override
  public final void execute() throws DialogException, UnknownTransactionKeyException {

    _form.parse();
    try {
      _receiver.paySale(key.value());
    }
    catch (NoSuchTransactionKeyException e) {
      throw new UnknownTransactionKeyException(key.value());
    }
  }
}
