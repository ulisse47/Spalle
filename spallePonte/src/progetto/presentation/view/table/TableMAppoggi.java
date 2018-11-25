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
import progetto.presentation.view.components.TableModelM2;
import progetto.presentation.view.components.TableModelMAppoggi;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableMAppoggi extends AbstractBaseTable {

	private static TableMAppoggi table;
	
	public static synchronized TableMAppoggi getInstance(){
		if ( table == null ){
			table = new TableMAppoggi();					
		}
		return table;
	}
	
	/**
	 * 
	 */
	private TableMAppoggi() {
		super();		
		this.setPreferredScrollableViewportSize(new Dimension(350, 100));
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractBaseTable#loadModel()
	 */
	protected TableModel loadModel() {
		TableModelMAppoggi model = new TableModelMAppoggi();
		return model;
	}

	

}
