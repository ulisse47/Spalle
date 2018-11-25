/*
 * Created on 9-gen-2005
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
public class CaricoForze {

	private double fx;
	private double fy;
	private double fz;
	private double mx;
	private double my;
	
	/**
	 * 
	 */
	public CaricoForze() {
		super();		
	}
	
	/**
	 * 
	 */
	public CaricoForze( double fx, double fy, double fz, double mx, double my ) {
		super();		
		this.fx = fx;
		this.fy = fy;
		this.fz = fz;
		this.mx = mx;
		this.my = my;
	}
	
	

	public double getFx() {
		return fx;
	}
	public void setFx(double fx) {
		this.fx = fx;
	}
	public double getFy() {
		return fy;
	}
	public void setFy(double fy) {
		this.fy = fy;
	}
	public double getFz() {
		return fz;
	}
	public void setFz(double fz) {
		this.fz = fz;
	}
	public double getMx() {
		return mx;
	}
	public void setMx(double mx) {
		this.mx = mx;
	}
	public double getMy() {
		return my;
	}
	public void setMy(double my) {
		this.my = my;
	}	
	
}
