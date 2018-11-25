/*
 * Created on 9-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MainContainerPanel extends JPanel{

	private JTabbedPane tab;
	
	/**
	 * 
	 */
	public MainContainerPanel() {
		super();
		tab = new JTabbedPane();
	/*	tab.add( "spalla",  SpallaContainerPanel.getInstance() );
		tab.add( "carichi", CarichiContainerPanel.getInstance() );
		tab.add( "fondazione", FondazioneContainerPanel.getInstance() );
		tab.add( "portanza", VerificaPortanzaContainerPanel.getInstance() );
        */
                tab.add( SpallaContainerPanel.getInstance() );
	//	tab.add(CarichiContainerPanel.getInstance());
	//	tab.add( FondazioneContainerPanel.getInstance() );
	//	tab.add( VerificaPortanzaContainerPanel.getInstance() );
         
                
		//test
		/*JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
		panel.add( FondazioniView.getInstance(), BorderLayout.CENTER );
		
		tab.add( "test panel ", panel );*/
				
		setLayout( new BorderLayout() );
		add( tab, BorderLayout.CENTER );	
	
	}

}
