package progetto.presentation;

import java.awt.event.ActionEvent;

import progetto.presentation.util.Command;
import progetto.presentation.util.CommandFactory;


/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 13-set-2003
 * Time: 16.14.08
 * To change this template use Options | File Templates.
 */
public class DefaultRequestHelper extends AbstractRequestHelper{

   	/**
	 * @param request
	 */
	public DefaultRequestHelper( ActionEvent request) {
		
		super( request );
		
	}

	private CommandFactory commandFactory = CommandFactoryImpl.getInstance();


    public Command getCommand(){
      return commandFactory.createCommand( getRequest().getActionCommand());
   }
}
