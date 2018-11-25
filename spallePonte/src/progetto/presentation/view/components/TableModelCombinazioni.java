/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.util.ArrayList;
import java.util.Iterator;

import progetto.model.bean.Carico;
import progetto.model.bean.Combinazione;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelCombinazioni extends AbstractBaseTableModel {

	/**
	 * 
	 */
	public TableModelCombinazioni() {
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
		Iterator lista = spalla.getCombinazioni().iterator();
		
		ArrayList carichi = spalla.getCarichi();
		int size = carichi.size();
				
		Object[][] rowData = 
			new Object[ spalla.getCombinazioni().size() ][7 + size];
		Combinazione combinazione;
		ArrayList combos;
		int id = 0;
		while( lista.hasNext()){
			combinazione = (Combinazione)lista.next();
			combos = combinazione.getCombo();			
			rowData[id][0] = combinazione.getName();
			for ( int i = 0; i < size + 6; i++ ){
				rowData[id][i + 1] = ( Double )combos.get( i );
			} 			
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
		Spalla spalla = SpallaManager.getInstance().getCurrentSpalla();	
		ArrayList carichi = spalla.getCarichi();
		int size = carichi.size();
		Carico carico;		
		
		String[] headers = new String[7 + size];
		headers[0]="Combo";
		headers[1]="Pesi propri";
		headers[2]="Sovracc. q";
		headers[3]="K terreno (stat)";
		headers[4]="K terreno (sism)";
		headers[5]="Inerzie sismiche x";
		headers[6]="Inerzie sismiche y";
		
		
		for ( int i = 0; i < size; i++  ){
			carico = ( Carico )carichi.get( i );
			headers[ 7 + i ] = carico.getName(); 
		}
		
		return headers;
	}

}
