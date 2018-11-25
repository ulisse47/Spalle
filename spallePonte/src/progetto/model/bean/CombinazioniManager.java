/*
 * Created on 10-gen-2005
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
public class CombinazioniManager {

	private static CombinazioniManager man;	
	
	private int carichiFissi = 6;
	
	public static synchronized CombinazioniManager getInstance(){
		if (  man == null ){
			man = new CombinazioniManager();
		}
		return man;
	}
	
	/**
	 * 
	 */
	private CombinazioniManager() {
		super();
	}

	/**
	 * @param combinazioni
	 */
	public void addCarico(ArrayList combinazioni) {
		Combinazione combo;
		for ( int i = 0; i < combinazioni.size(); i++ ){
			combo = ( Combinazione )combinazioni.get( i );
			combo.getCombo().add( new Double( 0 ) );
		}
		
	}
	
          /**
	 * @param index
         * @param combinazioni
	 */
	public void moveUpCarico(int index, ArrayList combinazioni) {
		Combinazione combo;
		for ( int i = 0; i < combinazioni.size(); i++ ){
			combo = ( Combinazione )combinazioni.get( i );
			ArrayList cm = combo.getCombo();
                        Double d= (Double)cm.get(index+5);
                        cm.remove(index+5);
                        cm.add(index+5-1, d);
                }
		
	}
        
         /**
	 * @param index
         * @param combinazioni
	 */
	public void moveDownCarico(int index, ArrayList combinazioni) {
		Combinazione combo;
		for ( int i = 0; i < combinazioni.size(); i++ ){
			combo = ( Combinazione )combinazioni.get( i );
			ArrayList cm = combo.getCombo();
                        Double d= (Double)cm.get(index+5);
                        cm.remove(index+5);
                        cm.add(index+5+1, d);
                }
		
	}
        
	/**
	 * 
	 * @param nome
	 * @param combinazioni
	 * @param numeroCarichiMobili
	 */
	public void addCombinazione( String nome, ArrayList combinazioni, int numeroCarichiMobili ){
			combinazioni.add( 
					new Combinazione( nome, carichiFissi + numeroCarichiMobili ) );
	}
	
	
	/**
	 * @param combinazioni
	 */
	public void deleteCarico(ArrayList combinazioni, int id_carico ) {
		Combinazione combo;
		for ( int i = 0; i < combinazioni.size(); i++ ){
			combo = ( Combinazione )combinazioni.get( i );
			combo.getCombo().remove( id_carico + carichiFissi );
		}		
	}

}
