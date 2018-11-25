/*
 * Created on 29-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.dispatcher;

import javax.swing.JOptionPane;


/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ErrorDispatcher implements RequestDispatcherInt {

	private Exception ex;
	
	
	/**
	 * @param ex
	 */
	public ErrorDispatcher(Exception ex) {
		this.ex = ex;
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.dispatcher.RequestDispatcherInt#forward(java.util.Properties, java.util.Properties)
	 */
	public void forward(Object request )
			throws Exception {
		ex.printStackTrace();
		JOptionPane.showMessageDialog(null, ex.getMessage(),
				"Alert", JOptionPane.ERROR_MESSAGE );

	}

}
