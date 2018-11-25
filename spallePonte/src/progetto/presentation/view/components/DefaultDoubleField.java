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
public class DefaultDoubleField extends AbstractNumericField {
	
		
	/**
	 * 
	 */
	public DefaultDoubleField( ) {
		super();	
		setRound( 3 );
		setPreferredSize(new Dimension(100, 21));
	}	
	
	/**
	 * 
	 */
	public DefaultDoubleField( int witdh ) {
		super();	
		setRound( 3 );
		setPreferredSize(new Dimension( witdh , 21));
	}	
	
	
	/**
	 * 
	 */
	public DefaultDoubleField( int round, boolean editable ) {
		this();
		setEditable( editable );
		setRound( round );
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
