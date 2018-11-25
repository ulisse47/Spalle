/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.table.TableModel;

import progetto.exception.ParsingDataException;
import progetto.presentation.view.util.InputComponent;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractDataParser {

	/**
	 * 
	 */
	public AbstractDataParser() {
		super();
	}
	
	
	/**
	 * @param model
	 */
	public void parseData(TableModel model) throws ParsingDataException  {
		
		/*Object value = null;
		String colName = null;
		try {
			for (int i = 0; i < model.getRowCount(); i++) {
				for (int col = 0; col < model.getColumnCount(); col++) {
					value = model.getValueAt(i, col);
					colName = model.getColumnName(col);
					if (value instanceof Double) {
							
					}

				}
			}
		} catch (Exception e) {
			throw new ParsingDataException(colName, e.getMessage());
		}*/
	}
	
	
	/**
	 * 
	 * @param inputs
	 * @throws ParsingDataException
	 */
	public void parseData( Hashtable inputs ) throws ParsingDataException {
		InputComponent comp;
		String key;
		String textValue;
		Set keys = inputs.keySet();
		Iterator iter = keys.iterator();
		
		while ( iter.hasNext() ){
			 key = ( String )iter.next();
			 comp = ( InputComponent )inputs.get( key );			 
			 if ( comp.isNumeric() ) {
			 	parseValue( key, comp );
			 }
		}				
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @throws ParsingDataException
	 */
	public void parseValue( String name, InputComponent value ) throws ParsingDataException{
		
		try {
			value.getInputValue();
		}catch ( Exception  ex ){
			throw new ParsingDataException( name, ex.getMessage() );
		}
		
	}
	

}
