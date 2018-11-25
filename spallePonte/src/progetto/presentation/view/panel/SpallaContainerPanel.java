/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import progetto.presentation.view.components.DefaultButton;
import progetto.presentation.view.util.ViewComponent;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpallaContainerPanel extends JPanel implements ViewComponent {

	private JSplitPane mainSplitPane;//vertical
	private JScrollPane leftScrollPane;
	
	private JSplitPane rightSplitPane;//horizontal
	private JScrollPane rightBottomScrollPane;//appoggi
	
	JPanel panelButton;
	
	private static SpallaContainerPanel panel;
	
	public static synchronized SpallaContainerPanel getInstance(){
		if ( panel == null ){
			panel = new SpallaContainerPanel();
		}
		return panel;
	}
	
	/**
	 * 
	 */
	private void init(){
		mainSplitPane = new JSplitPane();
		mainSplitPane.setOrientation( JSplitPane.HORIZONTAL_SPLIT );
		
		leftScrollPane = new JScrollPane();
		rightSplitPane = new JSplitPane();
		rightSplitPane.setOrientation( JSplitPane.VERTICAL_SPLIT );
		mainSplitPane.setLeftComponent( leftScrollPane );
		mainSplitPane.setRightComponent( rightSplitPane );
		
		JSplitPane splitPaneDisegni = new JSplitPane();
		splitPaneDisegni.setBackground( Color.BLACK );
		splitPaneDisegni.setTopComponent( CarpenteriaSpallaView.getInstance() );
		splitPaneDisegni.setBottomComponent( SezioneSpallaView.getInstance() );
		CarpenteriaSpallaView.getInstance().setMinimumSize( new Dimension( 100,400 ) );
		CarpenteriaSpallaView.getInstance().setPreferredSize( new Dimension( 270,400 ) );
		SezioneSpallaView.getInstance().setMinimumSize( new Dimension( 100,400 ) );
		SezioneSpallaView.getInstance().setPreferredSize( new Dimension( 150,400 ) );
		
		rightBottomScrollPane = new JScrollPane();
//		rightTopScrollPane = new JScrollPane();
		rightSplitPane.setTopComponent( splitPaneDisegni );
		rightSplitPane.setBottomComponent( rightBottomScrollPane  );
				
		//rightTopScrollPane.setBorder( new TitledBorder( "Pianta" ) );		
		//rightTopScrollPane.getViewport().add( CarpenteriaSpallaView.getInstance() );  		
		//rightTopScrollPane.setMinimumSize( new Dimension( 200, 200 ) );
		
		rightBottomScrollPane.setBorder( new TitledBorder( "Appoggi" ) );		
		rightBottomScrollPane.getViewport().add( new AppoggiPanel() );  
		rightBottomScrollPane.setMinimumSize( new Dimension( 200, 100 ) );
		
		leftScrollPane.getViewport().add(  SpallaInputDataPanel.getInstance() );		
		SpallaInputDataPanel.getInstance().setMaximumSize( new Dimension( 215, 600 ) );
		leftScrollPane.setMinimumSize( new Dimension( 230, 600 ) );
		
		panelButton = new JPanel();
		panelButton.add( new DefaultButton( "salva", "SalvaFile" ) );
		panelButton.add( new DefaultButton( "aggiungi appoggio","addAppoggio" ) );
		panelButton.add( new DefaultButton( "elimina appoggio","deleteAppoggio" ) );
		panelButton.add( new DefaultButton( "open","open" ) );
		panelButton.add( new DefaultButton( "stampa","stampa" ) );
		
				
		setLayout( new BorderLayout() );
		//add( panelButton, BorderLayout.SOUTH );
		add( mainSplitPane, BorderLayout.CENTER );		
	}
	
	/**
	 *
	 */
	private SpallaContainerPanel() {
		super();
		init();		
						
		//add( SpallaInputDataPanel.getInstance(), BorderLayout.CENTER );
		//add( panelButton, BorderLayout.SOUTH );
        //add( CarpenteriaSpallaView.getInstance() ,BorderLayout.CENTER );
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.util.ViewComponent#refreshView()
	 */
	public void refreshView() {
		repaint();
		
	}



}
