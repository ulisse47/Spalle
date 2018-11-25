/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.util.ArrayList;
import java.util.Iterator;

import progetto.model.bean.Combinazione;
import progetto.model.bean.Spalla;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelM2 extends AbstractBaseTableModel {

	/**
	 * 
	 */
	public TableModelM2() {
		super();
		rowData = loadRowData();
		header = loadHeaders();	
	}
	
	/**
	 * 
	 * @return
	 */
	public Object[][] loadRowData(){
		SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
		Spalla spalla = del.loadSpallaCorrenteFromModel();		
		Iterator lista = spalla.getCombinazioni().iterator();
		
		Object[][] rowData = 
			new Object[ spalla.getCombinazioni().size() ][ 6 ];
		Combinazione combinazione;
		ArrayList combos;
		int id = 0;
		//Calcoli calcoli = new Calcoli();
		double[] m1;
		while( lista.hasNext()){
			combinazione = (Combinazione)lista.next();
			m1 = del.getM2Combo( id );			
			rowData[id][0] = combinazione.getName();
			rowData[id][1] = new Double( m1[0] );
			rowData[id][2] = new Double( m1[1] );
			rowData[id][3] = new Double( m1[2] );
			rowData[id][4] = new Double( m1[3] );
			rowData[id][5] = new Double( m1[4] );
			id++;
		}	
		
		return rowData;
	}
	
	public boolean isCellEditable(int row, int col) {
		//if ( col == 0 ) return false;
		return false;
	}
	/**
	 * 
	 * @return
	 */
	public String[] loadHeaders(){
		SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
		Spalla spalla = del.loadSpallaCorrenteFromModel();		
				
		String[] headers = new String[6];
		headers[0]="combo";
		headers[1]="Fx(kN)";
		headers[2]="Fy(kN)";
		headers[3]="Fz(kN)";	
		headers[4]="Mx(kNm)";
		headers[5]="My(kNm)";
			
				
		return headers;
	}

}
