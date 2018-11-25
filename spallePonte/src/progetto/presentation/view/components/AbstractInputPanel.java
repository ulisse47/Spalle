/*
 * Created on 28-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import progetto.presentation.view.util.InputComponent;
import progetto.presentation.view.util.ViewComponent;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractInputPanel extends JPanel implements ViewComponent {
	
	protected Hashtable inputs = new Hashtable();
	protected Hashtable outputs = new Hashtable();
	protected GridBagLayout grid = new GridBagLayout();
	private int xLabel = 150;
	private int yLabel = 15;
		
	/**
	 * 
	 */
	public AbstractInputPanel() {
		super();
		setLayout( grid );
		init();
		refreshView();
	}
	
	protected abstract void init();
			
	/**
	 * 
	 * @return
	 */
	public Hashtable getInputData(){
		
		InputComponent field;
		String key;
		Hashtable data = new Hashtable();
		Set keys = inputs.keySet();
		Iterator iter = keys.iterator();
		while ( iter.hasNext() ){
			 key = ( String )iter.next();
			 field = ( InputComponent )inputs.get( key );
			 data.put( key, field );	
		}		
		return data;		
	}
	
	/**
	 * 
	 * @param field
	 * @param row
	 * @param column
	 * @param name
	 * @param title
	 */
	public void addInput( JComponent field ,int row, int column, String name, String title ){
		JLabel label = new JLabel( );
		label.setText( title );
		label.setHorizontalAlignment( SwingConstants.LEFT );
		label.setPreferredSize(new Dimension( xLabel /*100*/, /*15*/ yLabel));
		
		add( label,   new GridBagConstraints( column, row, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, 
			GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 20, 0));
		add( field,   new GridBagConstraints( column + 1, row, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, 
				GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 20, 0));
	}	
	
	
	public void addInputField( JComponent field ,int row, int column, String name, String title ){
		addInput( field , row , column , name , title );
		inputs.put( name, field );	
	}
	
	public void addOutputField( JComponent field ,int row, int column, String name, String title ){
		addInput( field , row , column , name , title );
		//inputs.put( name, field );	
	}
	
	/**
	 * 
	 * @param title
	 * @param titlePanel
	 * @param rowPanel
	 * @param columnPanel
	 * @return
	 */
	public JPanel addInputPanel( String titlePanel, int rowPanel, int columnPanel ){
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout( new GridBagLayout() );
		TitledBorder titledBorder = 
			new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151)),
					titlePanel );
		innerPanel.setBorder( titledBorder );		
		
		add( innerPanel,   new GridBagConstraints( columnPanel , rowPanel, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, 
				GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 0, 0));	
		return innerPanel;
	}	
	
	/**
	 * 
	 * @param innerPanel
	 * @param field
	 * @param row
	 * @param column
	 * @param name
	 * @param title
	 * @param titlePanel
	 * @param rowPanel
	 * @param columnPanel
	 */
	public void addInput( JPanel innerPanel, JComponent field ,int row, int column, 
			String name, String title ){
			
		JLabel label = new JLabel( );
		label.setText( title );
		label.setHorizontalAlignment( SwingConstants.LEFT );
		label.setPreferredSize(new Dimension( xLabel /*100*/, /*15*/ yLabel));
				
		innerPanel.add( label,   new GridBagConstraints( column, row, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, 
			GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 0, 0));
		innerPanel.add( field,   new GridBagConstraints( column + 1, row, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, 
				GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 0, 0));	
	}
	
	
	public void addInputField( JPanel innerPanel, JComponent field ,int row, int column, 
			String name, String title ){
		addInput( innerPanel,field, row, column, name, title );
		inputs.put( name, field );
	}
	
	public void addOutputField( JPanel innerPanel, JComponent field ,int row, int column, 
			String name, String title ){
		addInput( innerPanel,field, row, column, name, title );
		outputs.put( name, field );
	}

	
	public int getXLabel() {
		return xLabel;
	}
	public void setXLabel(int label) {
		xLabel = label;
	}
	public int getYLabel() {
		return yLabel;
	}
	public void setYLabel(int label) {
		yLabel = label;
	}
}
