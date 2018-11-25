/*
 * Created on 16-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.model.bean;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TerrenoManager {
	
	private static TerrenoManager terrenoMan;

	protected static synchronized TerrenoManager getInstance(){
		if ( terrenoMan == null ){
			terrenoMan = new TerrenoManager();
		}
		return terrenoMan;
	}

             
	/**
	 * 
	 */
	private TerrenoManager() {
		super();
	}

}
