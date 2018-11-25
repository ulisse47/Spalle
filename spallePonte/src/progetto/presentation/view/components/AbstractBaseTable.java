/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import progetto.presentation.view.util.ViewComponent;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractBaseTable extends JTable implements ViewComponent {

	public AbstractBaseTable( TableModel model) {
		super(model);
	}
	
	public AbstractBaseTable() {
		super();
		refreshView();
	}
	
	/**
	 * 
	 * @return
	 */
	protected abstract TableModel loadModel();

	/**
	 * 
	 *
	 */
	public void refreshView(){
		setModel( loadModel() );
	}
	
	public Component prepareEditor(TableCellEditor editor, int row, int col) {
		Component result = super.prepareEditor(editor, row, col);
		if (result instanceof JTextField) {
			((JTextField) result).selectAll();
			((JTextField) result).requestFocus();
		}
		return result;
	}

}