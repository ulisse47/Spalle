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
public class Combinazione {
	
	public String toString() {
		return getName();
	}
	private String name = "";
	
	/** uguale al numero dei carichi */
	private ArrayList combo = new ArrayList();
		
	private void addCarichiFissi( int k ){
		for ( int i = 0; i < k; i++ ){
			combo.add( new Double( 1 ) );
		}
		
	}
	
	public Combinazione(){}
	
	/**
	 * 
	 */
	public Combinazione( String nome, int fissi ) {
		super();
		addCarichiFissi( fissi );
		this.name = nome;
	}
	public ArrayList getCombo() {
		return combo;
	}
	public void setCombo(ArrayList combo) {
		this.combo = combo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
