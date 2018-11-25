/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.util.Iterator;

import progetto.model.bean.Palo;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelPali extends AbstractBaseTableModel {

	/**
	 * 
	 */
	public TableModelPali() {
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
		Iterator lista = del.loadPalificataFromModel().iterator();
		int nPali = del.loadPalificataFromModel().size();
		Object[][] rowData = new Double[ nPali ][5];
		Palo palo;
		int id = 0;
		
		double[] sigmaN = del.getNpalificata();
		
		while( lista.hasNext()){
			palo = (Palo)lista.next();
			rowData[id][0] = new Double( id +1);
			rowData[id][1] = new Double( palo.getX() );
			rowData[id][2] = new Double( palo.getY() );
			rowData[id][3] = new Double( palo.getDiametro() );		
			rowData[id][4] = new Double( sigmaN[ id ] );		
			//rowData[id][4] = new Double( 0 );		
			id++;
		}	
		
		return rowData;
	}
	
	public boolean isCellEditable(int row, int col) {
		if ( col == 0 ) return false;
		return true;
	}
	/**
	 * 
	 * @return
	 */
	public String[] loadHeaders(){
		String[] headers = new String[5];
		headers[0]="Numero palo";
		headers[1]="xi(m)";
		headers[2]="yi(m)";
		headers[3]="diametro(m)";	
		headers[4]="N(kN)";	
		
		return headers;
	}

}
