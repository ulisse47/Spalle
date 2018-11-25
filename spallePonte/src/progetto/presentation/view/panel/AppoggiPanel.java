/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import progetto.presentation.view.table.TableAppoggi;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AppoggiPanel extends JPanel {

	private JScrollPane tableAppoggiScrollPane;
	
	/**
	 * 
	 */
	public AppoggiPanel() {
		super();
		setLayout( new BorderLayout());  
		tableAppoggiScrollPane = new JScrollPane();
		tableAppoggiScrollPane.getViewport().add( TableAppoggi.getInstance() );
		add( tableAppoggiScrollPane , BorderLayout.CENTER );
	}	

}
