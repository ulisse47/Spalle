/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.util.Hashtable;

import javax.swing.Action;
import javax.swing.JButton;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractButton extends JButton {

	private Hashtable inputs;
	
	
	/**
	 * 
	 */
	public AbstractButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public AbstractButton(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public AbstractButton(Action arg0) {
		super(arg0);
	}
	
	public Hashtable getInputs() {
		return inputs;
	}
	public void setInputs(Hashtable inputs) {
		this.inputs = inputs;
	}
	

}
