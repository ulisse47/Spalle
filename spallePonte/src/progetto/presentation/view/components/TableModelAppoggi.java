/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.util.Iterator;

import progetto.model.bean.Appoggio;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelAppoggi extends AbstractBaseTableModel {

	/**
	 * 
	 */
	public TableModelAppoggi() {
		super();
		rowData = loadRowData();
		header = loadHeaders();	
	}
	
	/**
	 * 
	 * @return
	 */
	public Object[][] loadRowData(){
		Spalla spalla = SpallaManager.getInstance().getCurrentSpalla();		
		Iterator lista = spalla.getAppoggi().iterator();
		Object[][] rowData = new Object[ spalla.getAppoggi().size() ][4];
		Appoggio appoggio;
		int id = 0;
		while( lista.hasNext()){
			appoggio = (Appoggio)lista.next();
			rowData[id][0] = new String( appoggio.getName() );
			rowData[id][1] = new Double( appoggio.getX() );
			rowData[id][2] = new Double( appoggio.getY() );
			rowData[id][3] = new Double( appoggio.getZ() );		
			id++;
		}	
		
		return rowData;
	}
	
	public boolean isCellEditable(int row, int col) {
		//if ( col == 0 ) return false;
		return true;
	}
	/**
	 * 
	 * @return
	 */
	public String[] loadHeaders(){
		String[] headers = new String[4];
		headers[0]="Appoggio";
		headers[1]="xi(m)";
		headers[2]="yi(m)";
		headers[3]="zi(m)";	
		
		return headers;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if ( aValue instanceof String ) {
			if(column!=0)aValue = new Double( ( String )aValue );
			else aValue = new String( ( String )aValue );

		} 		
		super.setValueAt(aValue, row, column);
	}

}
