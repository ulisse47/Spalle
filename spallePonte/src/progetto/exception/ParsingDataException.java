/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.exception;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ParsingDataException extends Exception {

	/**
	 * 
	 */
	public ParsingDataException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ParsingDataException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ParsingDataException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ParsingDataException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param value
	 * @param message
	 */
	public ParsingDataException(String name, String message) {
		super ( "input=" + name + "; cause=" + message );		
	}

}
