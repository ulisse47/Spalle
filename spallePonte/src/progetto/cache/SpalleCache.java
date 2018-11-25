/*
 * Created on 24-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.cache;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpalleCache extends AbstractCache {
	
	private static SpalleCache cache;
	public static String sigma_wx_wy = "sigma_wx_wy";
	public static String mverifica = "mverifica";
	public static String npalificata = "npalificata";
	public static String m1Combo = "m1Combo";

	/**
	 * 
	 * @return
	 */
	public static synchronized SpalleCache getInstance(){
		if ( cache == null ){
			cache = new SpalleCache();
		}
		return cache;
	}
	
}
