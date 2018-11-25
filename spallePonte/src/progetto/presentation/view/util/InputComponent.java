/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.util;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface InputComponent {
	
	public Object getInputValue() throws Exception ;
	
	public boolean isNumeric();
	
	public void setValue( Object newValue ) throws Exception;
		
}
