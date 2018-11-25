/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import it.ccprogetti.spalleponte.netbeans.actions.AddCaricoAction;
import it.ccprogetti.spalleponte.netbeans.actions.DeleteCaricoAction;
import it.ccprogetti.spalleponte.netbeans.actions.DeleteCaricoAction;
import java.awt.BorderLayout;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.SwingUtilities;
import org.openide.util.actions.SystemAction;
import progetto.presentation.view.table.TableCarichi;



/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CarichiPanel extends JPanel {

	private JScrollPane tableCarichiScrollPane = new JScrollPane();

    public TableCarichi getCarichi() {
        return carichi;
    }

    public JScrollPane getTableCarichiScrollPane() {
        return tableCarichiScrollPane;
    }
	
        
	/**
	 * 
	 */
	public CarichiPanel() {
		super();
 
                
                
 		setLayout( new BorderLayout());  
		tableCarichiScrollPane.getViewport().add( TableCarichi.getInstance() );
		add( tableCarichiScrollPane , BorderLayout.CENTER );
              
                           
	}

     TableCarichi carichi  = TableCarichi.getInstance();



}
