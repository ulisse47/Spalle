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
public class Appoggio {

	private String name;
	
	private double x;
	private double y;
	private double z;
	
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 */
	public Appoggio( String name,double x, double y, double z  ) {
		this.name=name;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * 
	 */
	public Appoggio() {
		name="App_";
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
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
}
