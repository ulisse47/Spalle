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
import progetto.presentation.view.components.TableModelCombinazioni;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableCombinazioni extends AbstractBaseTable {

	private static TableCombinazioni table;
	
	public static synchronized TableCombinazioni getInstance(){
		if ( table == null ){
			table = new TableCombinazioni();					
		}
		return table;
	}
	
	/**
	 * 
	 */
	private TableCombinazioni() {
		super();		
		this.setPreferredScrollableViewportSize(new Dimension(350, 100));
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractBaseTable#loadModel()
	 */
	protected TableModel loadModel() {
		TableModelCombinazioni model = new TableModelCombinazioni();
		return model;
	}

	

}
