/*
 * Created on 9-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.model.bean;

import java.util.ArrayList;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CaricoManager {
	
	private static CaricoManager caricoManager;
		
	
	/**
	 * 
	 * @return
	 */
	protected static synchronized CaricoManager getInstance(){
		if ( caricoManager == null ){
			caricoManager = new CaricoManager();
		}
		return caricoManager;
	}
		
	
	/**
	 * 
	 */
	private CaricoManager() {
		super();		
	}
	
	

	/**
	 * Devo aggiungere un CaicoForza ad ogni carico
	 */
	public void addAppoggio( ArrayList carichi ) {
		for ( int i = 0; i < carichi.size(); i++ ){
			Carico carico = ( Carico )carichi.get( i );
			carico.getForzeAppoggi().add( new CaricoForze() );
		}
	}

	/**
	 * Ad ogni carico devo rimuovere il caricoForza corrispndente 
	 * all'appoggio @param id_appoggio
	 * @param id_appoggio
	 */
	public void deleteAppoggio(ArrayList carichi, Appoggio id_appoggio) {
		for ( int i = 0; i < carichi.size(); i++ ){
			Carico carico = ( Carico )carichi.get( i );
			carico.getForzeAppoggi().remove( id_appoggio );
		}		
	}
}
