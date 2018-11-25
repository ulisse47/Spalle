/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

/* @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FormattedDoubleField extends DefaultDoubleField {
	
	
	/**
	 * 
	 */
	public FormattedDoubleField( int round, boolean editable, int width ) {
		super( width );
		setEditable( editable );
		setRound( round );
	}
	
	
	
	public String formatValue(Object value) {
		//Double dValue = ( Double )value; 
		formatter.setMinimumFractionDigits( getRound() );
		formatter.setMaximumFractionDigits( getRound() );
		return formatter.format( value );		
	}
	
	/**
	 * 
	 */
	public Object getInputValue() {
		//return this.getValue();
		String value = ( String )super.getInputValue();
		//value = value.replace( ',','.' );
		return new Double( value );
	}

	

}
