/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;


/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultCheckBox extends AbstractCheckBox {

	/**
	 * 
	 */
	public DefaultCheckBox() {
		super();
	}

	/**
	 * @param arg0
	 */
	public DefaultCheckBox(String arg0) {
		super(arg0);
	}
	

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.InputComponent#getInputValue()
	 */
	public Object getInputValue() {
		
		return new Boolean( super.isSelected());	
	}

	

	
}
