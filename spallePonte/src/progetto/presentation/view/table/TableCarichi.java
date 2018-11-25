/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.table;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import progetto.model.bean.Carico;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.presentation.view.components.AbstractBaseTable;
import progetto.presentation.view.components.TableModelCarichi;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableCarichi extends AbstractBaseTable {

	private static TableCarichi table;
	
	public static synchronized TableCarichi getInstance(){
		if ( table == null ){
			table = new TableCarichi();

		}
		return table;
	}
	
	/**
	 * 
	 */
	private TableCarichi() {
		super();		
		this.setPreferredScrollableViewportSize(new Dimension(200, 100));
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractBaseTable#loadModel()
	 */
	protected TableModel loadModel() {
		TableModelCarichi model = new TableModelCarichi();
		return model;
	}

	

}
