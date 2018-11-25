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
public class VerticaleManager {
	
	private static VerticaleManager verticaleMan;

	protected static synchronized VerticaleManager getInstance(){
		if ( verticaleMan == null ){
			verticaleMan = new VerticaleManager();
		}
		return verticaleMan;
	}

        public void addStratoTerreno(ArrayList stratiTerreno, double h,double fi, double gamma,
                double cu, double k, double alfa){
        stratiTerreno.add(new Terreno(h,fi, gamma, cu, k, alfa));
        
        }
       
	/**
	 * 
	 */
	private VerticaleManager() {
		super();
	}

}
