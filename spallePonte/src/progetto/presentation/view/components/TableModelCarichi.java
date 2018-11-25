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
import progetto.model.bean.Carico;
import progetto.model.bean.CaricoForze;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelCarichi extends AbstractBaseTableModel {

	/**
	 * 
	 */
	public TableModelCarichi() {
		super();
		rowData = loadRowData();
		header = loadHeaders();	
	}
	
	/**
	 * 
	 * @return
	 */
	public Object[][] loadRowData(){
		SpallaManager man = SpallaManager.getInstance();
		Carico current = man.getCurrentCarico();
		Spalla spalla = man.getCurrentSpalla();
		ArrayList appoggi = spalla.getAppoggi();
		
		if ( current == null ) {
			return new Double[ 0 ][6];
		}		
		
		Carico c = man.getCurrentCarico();
		
		Iterator lista = c.getForzeAppoggi().iterator();
		Object[][] rowData = 
			new Object[ man.getCurrentCarico().getForzeAppoggi().size()][6];
		
		CaricoForze forzeAppoggio;
		int id = 0;
//		if (c.isAtrito()) return null;
		while( lista.hasNext()){
			Appoggio ap = (Appoggio)appoggi.get(id);
			forzeAppoggio = (CaricoForze)lista.next();
			rowData[id][0] = new String( ap.getName() );
			rowData[id][1] = new Double( forzeAppoggio.getFx() );
			rowData[id][2] = new Double( forzeAppoggio.getFy() );
			rowData[id][3] = new Double( forzeAppoggio.getFz() );		
			rowData[id][4] = new Double( forzeAppoggio.getMx() );	
			rowData[id][5] = new Double( forzeAppoggio.getMy() );	
			id++;
			
		}
		
		return rowData;
	}
	
	public boolean isCellEditable(int row, int col) {
		if ( col == 0 ) return false;
		SpallaManager man = SpallaManager.getInstance();
		Carico c = man.getCurrentCarico();
		if(c.isAtrito()) return false;
		return true;
	}
	/**
	 * 
	 * @return
	 */
	public String[] loadHeaders(){
		String[] headers = new String[6];
		headers[0]="Appoggio N°";
		headers[1]="Fx(kN)";
		headers[2]="Fy(kN)";
		headers[3]="Fz(kN)";	
		headers[4]="Mx(kNm)";	
		headers[5]="My(kNm)";	
		
		return headers;
	}

}
