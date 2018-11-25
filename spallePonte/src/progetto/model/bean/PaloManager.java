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
public class PaloManager {
	
	private static PaloManager paloMan;

	protected static synchronized PaloManager getInstance(){
		if ( paloMan == null ){
			paloMan = new PaloManager();
		}
		return paloMan;
	}
	
	public void deletePalo( ArrayList pali , int id_palo ){
		pali.remove( id_palo );
	}
	
	public void addPalo( ArrayList pali ){
		pali.add( new Palo() );
	}	
	
	public void addPalo( ArrayList pali ,Palo palo){
		pali.add( palo );
	}	
        
	public void wisardPalificata(ArrayList pali){
         /*Object s = new String();
            Object cl = (Object) J (null,"Wizard Palificata",
                "Tipo sezioni metalliche",JOptionPane.INFORMATION_MESSAGE,
                null,s,s);
 
          if(cl!= null){
            try {
                prg.addSezioneMetallica(cl.getClass(),true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         */   
	}	
        

	/**
	 * 
	 */
	private PaloManager() {
		super();
	}

}
