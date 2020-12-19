package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;     
import pt.tecnico.po.ui.Input;       
import woo.Storefront;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.NoSuchSupplierKeyException;
/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {

  private Input<String> key;


  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    key = _form.addStringInput(Message.requestSupplierKey());

  }

  @Override
  public void execute() throws DialogException, UnknownSupplierKeyException {
    _form.parse();
    try {
      _display.addLine(_receiver.showSupplierTransactions(key.value()));
      _display.display();
    }
    catch (NoSuchSupplierKeyException e) {
        throw new UnknownSupplierKeyException(key.value());
    }
  }

}
