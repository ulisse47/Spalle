/*
 * Created on 24-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.cache;

import java.util.Hashtable;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractCache {

	private Hashtable cahedValues = new Hashtable();
	
	/**
	 * 
	 *
	 */
	public void refreshValue(){
		cahedValues = new Hashtable();
	}
	
	/**
	 * 
	 * @param value
	 * @param name
	 */
	public void addValue( Object value, String name ){
		//cahedValues.put( name, value );
	}	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Object getValue( String name ){
		return cahedValues.get( name );
	}	
}
