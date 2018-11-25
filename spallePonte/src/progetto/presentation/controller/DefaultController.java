package progetto.presentation.controller;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import progetto.presentation.DefaultRequestHelper;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;



/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 13-set-2003
 * Time: 15.25.15
 * To change this template use Options | File Templates.
 */
public class DefaultController extends AbstractAction {

    private void processRequest( ActionEvent request ) {
        RequestDispatcherInt next;
        Command command;
        try{
            RequestHelper helper = new DefaultRequestHelper( request );
            command = helper.getCommand();
            next = command.execute( helper );
            next.forward( request );
            
        }catch(Exception e){
            e.printStackTrace();
        }       
    }

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event ) {
		processRequest( event );
	}

 }



