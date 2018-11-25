/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.table;

import java.awt.Dimension;

import javax.swing.table.TableModel;

import progetto.presentation.view.components.AbstractBaseTable;
import progetto.presentation.view.components.TableModelM1;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableM1 extends AbstractBaseTable {

	private static TableM1 table;
	
	public static synchronized TableM1 getInstance(){
		if ( table == null ){
			table = new TableM1();					
		}
		return table;
	}
	
	/**
	 * 
	 */
	private TableM1() {
		super();		
		this.setPreferredScrollableViewportSize(new Dimension(350, 100));
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractBaseTable#loadModel()
	 */
	protected TableModel loadModel() {
		TableModelM1 model = new TableModelM1();
		return model;
	}

	

}
