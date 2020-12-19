package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
import woo.exceptions.*;
import woo.app.exceptions.*;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<Storefront> {

  private Input<Integer> key;


  public DoShowTransaction(Storefront receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    key = _form.addIntegerInput(Message.requestTransactionKey());

  }

  @Override
  public final void execute() throws DialogException {

    _form.parse();
    try {
      _display.addLine(_receiver.showTransaction(key.value()));
      _display.display();
    }
    catch (NoSuchTransactionKeyException e) {
        throw new UnknownTransactionKeyException(key.value());
    }
  }
}
