/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import progetto.presentation.view.util.InputComponent;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractTextField extends JTextField  implements
		InputComponent {

	private boolean numeric = true;
	
	/**
	 *  
	 */
	public AbstractTextField() {
		super();
		addFocusListener( new MyListener() );
	}
	
	public AbstractTextField( boolean editable ) {
		this();
		this.setEditable( editable );
	}
	

	public Object getInputValue() {
		return super.getText();
	}

	public boolean isNumeric() {
		return numeric;
	}

	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}
	
	public abstract String formatValue( Object value );

	/*
	 * (non-Javadoc)
	 * 
	 * @see progetto.presentation.view.components.InputComponent#setValue(java.lang.Object)
	 */
	public void setValue(Object newValue) throws Exception {
		setText( formatValue( newValue ) );
	}
			
}

class MyListener implements FocusListener {

	public void focusLost(FocusEvent e) {
	}

	public void focusGained(FocusEvent e) {

		Class cl = e.getSource().getClass();
		JTextField source = (JTextField) e.getSource();

		source.selectAll();
		source.requestFocus();
	}
}