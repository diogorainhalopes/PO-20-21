package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                             
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.NoSuchClientKeyException;

/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<Storefront> {

  private Input<String> key;
  

  public DoShowClientTransactions(Storefront storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    key = _form.addStringInput(Message.requestClientKey());

  }

  @Override
  public void execute() throws DialogException, UnknownClientKeyException {
    _form.parse();
    try {
      _display.addLine(_receiver.showClientTransactions(key.value()));
      _display.display();
    }
    catch (NoSuchClientKeyException e) {
        throw new UnknownClientKeyException(key.value());
    }
  }

}
