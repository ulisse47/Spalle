/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import javax.swing.JCheckBox;

import progetto.presentation.view.util.InputComponent;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractCheckBox extends JCheckBox implements InputComponent{

	/**
	 * 
	 */
	public AbstractCheckBox() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public AbstractCheckBox(String arg0) {
		super(arg0);
	}	
	
	public boolean isNumeric() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.InputComponent#setValue(java.lang.Object)
	 */
	public void setValue(Object newValue) throws Exception {
		Boolean value = ( Boolean )newValue;
		setSelected( value.booleanValue() );		
	}

}
