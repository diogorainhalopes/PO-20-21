package woo.app.products;

import pt.tecnico.po.ui.Command;                             
import pt.tecnico.po.ui.DialogException;            
import pt.tecnico.po.ui.Input;                    
import woo.Storefront;                         
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.NoSuchProductKeyException;
/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {

  private Input<String> id;
  private Input<Integer> price;
  
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    id = _form.addStringInput(Message.requestProductKey());
    price = _form.addIntegerInput(Message.requestPrice());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.changePrice(id.value(), price.value());
    }
    catch (NoSuchProductKeyException e) {
        throw new UnknownProductKeyException(id.value());
    }
  }
}
