/*
 * Created on 9-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import javax.swing.JComboBox;

import progetto.presentation.view.util.InputComponent;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AbstractComboBox extends JComboBox implements InputComponent {

	/**
	 * 
	 */
	public AbstractComboBox() {
		super();		
	}	
	

	/* (non-Javadoc)
	 * @see progetto.presentation.view.util.InputComponent#getInputValue()
	 */
	public Object getInputValue() throws Exception {
		return super.getSelectedItem();
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.util.InputComponent#isNumeric()
	 */
	public boolean isNumeric() {
		return false;
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.util.InputComponent#setValue(java.lang.Object)
	 */
	public void setValue( Object newValue ) throws Exception {
		getModel().setSelectedItem( newValue );
	}

}
