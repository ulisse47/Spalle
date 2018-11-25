/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.table;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import progetto.presentation.view.components.AbstractBaseTable;
import progetto.presentation.view.components.TableModelOutputforzeLaterali;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableOutputForzeLaterali extends AbstractBaseTable {

	private static TableOutputForzeLaterali table;
	
	public static synchronized TableOutputForzeLaterali getInstance(){
		if ( table == null ){
			table = new TableOutputForzeLaterali();

		}
               table.setColumnSelectionAllowed(true);
               table.setRowSelectionAllowed(false);
		return table;
	}
	
	/**
	 * 
	 */
	private TableOutputForzeLaterali() {
		super();		
		this.setPreferredScrollableViewportSize(new Dimension(200, 100));
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractBaseTable#loadModel()
	 */
	protected TableModel loadModel() {
		TableModelOutputforzeLaterali model = new TableModelOutputforzeLaterali();
		return model;
                
	}
        

}
