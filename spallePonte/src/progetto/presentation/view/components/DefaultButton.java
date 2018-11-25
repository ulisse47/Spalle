/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import progetto.presentation.controller.DefaultController;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultButton extends AbstractButton {


	/**
	 * @param arg0
	 */
	public DefaultButton( String displayName, String actionName  ) {
		super( displayName );
		setActionCommand( actionName );
		addActionListener( new DefaultController() );
	}

}
