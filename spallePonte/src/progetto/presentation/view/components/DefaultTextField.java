/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.awt.Dimension;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultTextField extends AbstractTextField {
	
		
	/**
	 * 
	 */
	public DefaultTextField() {
		super();		
		setPreferredSize(new Dimension(100, 21));
		super.setNumeric( false );
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractTextField#formatValue(java.lang.Object)
	 */
	public String formatValue(Object value) {
		return value.toString();
	}	
	

}
