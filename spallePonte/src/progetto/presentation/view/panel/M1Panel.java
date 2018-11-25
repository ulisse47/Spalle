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

import progetto.presentation.view.table.TableM1;



/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class M1Panel extends JPanel {

	private JScrollPane tableCarichiScrollPane;
	
	/**
	 * 
	 */
	public M1Panel() {
		super();
		setLayout( new BorderLayout());  
		tableCarichiScrollPane = new JScrollPane();
		tableCarichiScrollPane.getViewport().add( TableM1.getInstance() );
		add( tableCarichiScrollPane , BorderLayout.CENTER );
	}	

}
