/*
 * Created on 10-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.util;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;

import progetto.presentation.view.util.InputComponent;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MyBeanUtils {
	
	/**
	 * Imposta i valori di tutti gli oggetti di tipo @link InputComponent contenuti in @param inputs 
	 * con i valori di @param bean. 
	 * @param bean
	 * @param inputs inputs contiene oggetti di tipo @link InputComponent.
	 * @throws Exception
	 */
	public static void setProperties( Object bean, Hashtable inputs )  throws Exception {
		InputComponent comp;
		String key;
		Object property;		
		Iterator keys = inputs.keySet().iterator();
		while ( keys.hasNext() ){
			key = ( String )keys.next();
			comp = ( InputComponent )inputs.get( key );
			property = PropertyUtils.getProperty( bean, key );
			comp.setValue( property );
		}	
	}
	
	
	public static Object getProperty( Object bean, String key )  throws Exception {
		return PropertyUtils.getProperty( bean, key );
	}
	
	
	/**
	 * Popola le proprietà di @param bean con quelle contenute in @param inputs.
	 * @param bean
	 * @param inputs contiene oggetti di tipo @link InputComponent.
	 * @throws Exception
	 */
	public static void populate( Object bean, Hashtable inputs )  throws Exception  {
		InputComponent comp;
		String key;
		
		Iterator keys = inputs.keySet().iterator();
		while ( keys.hasNext() ){
			key = ( String )keys.next();
			comp = ( InputComponent )inputs.get( key );
			PropertyUtils.setProperty( bean, key , comp.getInputValue() );	
		}		
	}

}
