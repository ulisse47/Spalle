/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.text.NumberFormat;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractNumericField extends AbstractTextField {

	private int round;
	protected NumberFormat formatter  = NumberFormat.getInstance();
	
	/**
	 *  
	 */
	public AbstractNumericField() {
		super();
		setNumeric( true );
	}
	/**
	 *  
	 */
	public AbstractNumericField( boolean editable, int round ) {
		super( editable );
		this.round = round;
		setNumeric( true );
	}
	
	
	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractTextField#formatValue(java.lang.Object)
	 */
	public String formatValue(Object value) {	
		//Double dValue = ( Double )value; 
		/*formatter.setMinimumFractionDigits( getRound() );
		formatter.setMaximumFractionDigits( getRound() );
		return formatter.format( value );*/
		return value.toString();
	}	
	

		
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
}
