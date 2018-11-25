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
public class Carico {

	private String name;
	
	//per ogni appoggio 
	private ArrayList forzeAppoggi = new ArrayList();
	
	//applico anche alla spalla (x calcolo M1)?
	private boolean agenteSuElevazioni;
	//applico anche agli appoggi?
	private boolean agenteSuAppoggi;
	//carico permanente
	private boolean permanente;
	//attrito parassita
	private boolean atrito;
	private double coefficienteAtrito;
	
	
	public boolean isAtrito() {
		return atrito;
	}

	public void setAtrito(boolean atrito) {
		this.atrito = atrito;
	}

	public double getCoefficienteAtrito() {
		return coefficienteAtrito;
	}

	public void setCoefficienteAtrito(double coefficienteAtrito) {
		this.coefficienteAtrito = coefficienteAtrito;
	}

	public boolean isPermanente() {
		return permanente;
	}

	public void setPermanente(boolean permanente) {
		this.permanente = permanente;
	}

	public boolean isAgenteSuAppoggi() {
		return agenteSuAppoggi;
	}

	public void setAgenteSuAppoggi(boolean agenteSuAppoggi) {
		this.agenteSuAppoggi = agenteSuAppoggi;
	}

	public boolean isAgenteSuElevazioni() {
		return agenteSuElevazioni;
	}

	public void setAgenteSuElevazioni(boolean agenteSuElevazioni) {
		this.agenteSuElevazioni = agenteSuElevazioni;
	}

	public Carico(){}
	
	/**
	 * @param appoggi
	 * 
	 */
	public Carico( String name, int appoggi ) {
		super();
		this.setName( name );
		permanente=true;
		agenteSuAppoggi=true;
		agenteSuElevazioni=true;
		
		for ( int i = 0; i < appoggi; i++ ){
			this.forzeAppoggi.add( new CaricoForze() );
		}
	}	
	
		
	public String toString() {
		return name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList getForzeAppoggi() {
		return forzeAppoggi;
	}
	public void setForzeAppoggi(ArrayList forzeAppoggi) {
		this.forzeAppoggi = forzeAppoggi;
	}
}
