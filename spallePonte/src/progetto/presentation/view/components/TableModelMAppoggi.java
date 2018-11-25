/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.util.ArrayList;
import java.util.Iterator;

import progetto.model.bean.Appoggio;
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
public class TableModelMAppoggi extends AbstractBaseTableModel {

	/**
	 * 
	 */
	public TableModelMAppoggi() {
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
		
		ArrayList appoggi = spalla.getAppoggi();
		int nAppoggi = appoggi.size();
		int nCombo = spalla.getCombinazioni().size();
		Object[][] rowData = 
			new Object[ nCombo * nAppoggi*2 ][ 5 ];
		
		Combinazione combinazione;
		ArrayList combos;
		int id = 0;
		int count = 0;
		
		//Calcoli calcoli = new Calcoli();
		double[][] m1;
		double[][] m2;
		while( lista.hasNext()){
			combinazione = (Combinazione)lista.next();
			m1 = del.getMAppoggiCombo( id );
			m2 = del.getMAppoggiPermanentiCombo(id);
			
			
			//n-appoggi per ogni combinazione
			for ( int i = 0; i < nAppoggi; i ++ ){
				Appoggio appoggio = (Appoggio)appoggi.get(i);
				rowData[count+i][0] = combinazione.getName();//colonna combinazione
				rowData[count+i][1] = appoggio;//colonna appoggio
				
				rowData[count+i][2] = new Double( m1[i][0] );
				rowData[count+i][3] = new Double( m1[i][1] );
				rowData[count+i][4] = new Double( m1[i][2] );
				//rowData[i][5] = new Double( m1[i][3] );
			}
			count += nAppoggi;
			//n-appoggi per ogni combinazione: NB solo permanenti
			for ( int i = 0; i < nAppoggi; i ++ ){
				Appoggio appoggio = (Appoggio)appoggi.get(i);
				rowData[count+i][0] = combinazione.getName() + " (Perm.)";//colonna combinazione
				rowData[count+i][1] = appoggio;//colonna appoggio
				
				rowData[count+i][2] = new Double( m2[i][0] );
				rowData[count+i][3] = new Double( m2[i][1] );
				rowData[count+i][4] = new Double( m2[i][2] );
				//rowData[i][5] = new Double( m1[i][3] );
			}
			count += nAppoggi;
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
				
		String[] headers = new String[5];
		headers[0]="combo";
		headers[1]="appoggio";
		headers[2]="Fx(kN)";
		headers[3]="Fy(kN)";
		headers[4]="Fz(kN)";	
							
		return headers;
	}

}
