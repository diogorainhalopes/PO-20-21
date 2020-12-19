package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.NoSuchClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.NoSuchProductKeyException;
/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<Storefront> {

  private Input<String> clientKey;
  private Input<String> prodKey;

  public DoToggleProductNotifications(Storefront storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    clientKey = _form.addStringInput(Message.requestClientKey());
    prodKey = _form.addStringInput(Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.toggleProductNotifications(clientKey.value(), prodKey.value());
    }
    catch (NoSuchClientKeyException e) {
        throw new UnknownClientKeyException(clientKey.value());
    }
    catch (NoSuchProductKeyException e) {
        throw new UnknownProductKeyException(prodKey.value());
    }
  }
}
