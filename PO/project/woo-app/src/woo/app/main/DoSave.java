package woo.app.main;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront; 
import java.io.*;
import woo.exceptions.*;


/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<Storefront> {

  private Input<String> fileName;

  /** @param receiver */
  public DoSave(Storefront receiver) {
    super(Label.SAVE, receiver);
    if (_receiver.isSaveNameEmpty())
        fileName = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    if (_receiver.getSave()){
      try {
          if(_receiver.isSaveNameEmpty()){
              _form.parse();
              _receiver.saveAs(fileName.value());
          }
          else _receiver.save();
      }
      catch (MissingFileAssociationException e) {
        e.printStackTrace();
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
