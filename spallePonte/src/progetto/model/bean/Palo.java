/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.model.bean;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Palo {

	
	private double x;
	private double y;
	private double diametro;
	
	/**
	 * 
	 */
	public Palo( double x, double y, double diametro  ) {
		this.x = x;
		this.y = y;
		this.diametro = diametro;
	}
	
	/**
	 * 
	 */
	public Palo() {
	}
	
	

	public double getDiametro() {
		return diametro;
	}
	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

}
