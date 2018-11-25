/*
 * Created on 9-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.awt.Dimension;
import progetto.presentation.controller.DefaultController;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultComboBox extends AbstractComboBox {

	/**
	 * 
	 */
	public DefaultComboBox(  String actionName  ) {
		super();
                setPreferredSize(new Dimension(100, 21));
		setActionCommand( actionName );
		addActionListener( new DefaultController() );
	}

}
