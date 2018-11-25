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
public class DefaultIntegerField extends AbstractNumericField {
	
		
	/**
	 * 
	 */
	public DefaultIntegerField() {
		super();
		setPreferredSize(new Dimension(50, 21));
	}
	
	
	/**
	 * 
	 */
	public Object getInputValue()  {
		return new Integer( ( String )super.getInputValue());
	}	
}
