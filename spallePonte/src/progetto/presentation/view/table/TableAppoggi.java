/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.table;

import java.awt.Dimension;

import javax.swing.table.TableModel;

import progetto.presentation.util.ExcelAdapter;
import progetto.presentation.view.components.AbstractBaseTable;
import progetto.presentation.view.components.TableModelAppoggi;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableAppoggi extends AbstractBaseTable {

	private static TableAppoggi table;
	
	public static synchronized TableAppoggi getInstance(){
		if ( table == null ){
			table = new TableAppoggi();	
			table.setCellSelectionEnabled(true);

			new ExcelAdapter(table);

		}
		return table;
	}
	
	/**
	 * 
	 */
	private TableAppoggi() {
		super();		
		this.setPreferredScrollableViewportSize(new Dimension(200, 100));
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractBaseTable#loadModel()
	 */
	protected TableModel loadModel() {
		TableModelAppoggi model = new TableModelAppoggi();
		return model;
	}

	

}
