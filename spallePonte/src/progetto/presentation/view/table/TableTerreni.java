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

import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.presentation.view.components.AbstractBaseTable;
import progetto.presentation.view.components.TableModelTerreni;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableTerreni extends AbstractBaseTable {

	private static TableTerreni table;
	
	public static synchronized TableTerreni getInstance(){
		if ( table == null ){
			table = new TableTerreni();

		}
		return table;
	}
	
	/**
	 * 
	 */
	private TableTerreni() {
		super();		
		this.setPreferredScrollableViewportSize(new Dimension(200, 100));
	}

	/* (non-Javadoc)
	 * @see progetto.presentation.view.components.AbstractBaseTable#loadModel()
	 */
	protected TableModel loadModel() {
		TableModelTerreni model = new TableModelTerreni();
		return model;
                
	}
        

}
