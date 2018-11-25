/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import java.awt.BorderLayout;


import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import progetto.presentation.view.table.TableTerreni;



/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TerreniPanel extends JPanel {

	private JScrollPane tableTerreniScrollPane;
      
        
	/**
	 * 
	 */
	public TerreniPanel() {
		super();
 
                
                
 		setLayout( new BorderLayout());  
		tableTerreniScrollPane = new JScrollPane();
		tableTerreniScrollPane.getViewport().add( TableTerreni.getInstance() );
		add( tableTerreniScrollPane , BorderLayout.CENTER );
              
                
	}


}
