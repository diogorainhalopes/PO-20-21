package woo.app.suppliers;

import pt.tecnico.po.ui.Command;   
import pt.tecnico.po.ui.DialogException;       
import pt.tecnico.po.ui.Input;      
import woo.Storefront;            


/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<Storefront> {


  public DoShowSuppliers(Storefront receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);
  }

  @Override
  public void execute() throws DialogException {
    String text = _receiver.showAllSuppliers();
    text = text.replaceAll("y\n", (Message.yes() + "\n"));
    text = text.replaceAll("n\n", (Message.no() + "\n"));

    _display.addLine(text);
    _display.display();

  }
}
