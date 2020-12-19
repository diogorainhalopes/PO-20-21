package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import java.io.*;
import woo.exceptions.*;
import woo.app.exceptions.*;


/**
 * Open existing saved state.
 */
public class DoOpen extends Command<Storefront> {

  private Input<String> fileName;

  /** @param receiver */
  public DoOpen(Storefront receiver) {
    super(Label.OPEN, receiver);
    fileName = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws FileOpenFailedException {
    try {
      _form.parse();
      _receiver.load(fileName.value());
    } 
    catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());
    }
  }
}
