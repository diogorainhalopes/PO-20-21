package woo.app.suppliers;

import pt.tecnico.po.ui.Command; 
import pt.tecnico.po.ui.DialogException;     
import pt.tecnico.po.ui.Input;        
import woo.Storefront;    
import woo.app.exceptions.UnknownSupplierKeyException;

import woo.exceptions.NoSuchSupplierKeyException;
/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {

  private Input<String> id;

  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    id = _form.addStringInput(Message.requestSupplierKey());
    
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      if (_receiver.toggleTransactions(id.value())){
        _display.addLine(Message.transactionsOn(id.value()));
      }
      else {
        _display.addLine(Message.transactionsOff(id.value()));
      }
      _display.display();
    }
    catch (NoSuchSupplierKeyException e) {
        throw new UnknownSupplierKeyException(id.value());
    }
  }

}
