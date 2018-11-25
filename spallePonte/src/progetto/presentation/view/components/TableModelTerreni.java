/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.util.ArrayList;

import progetto.model.bean.Terreno;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.Verticale;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelTerreni extends AbstractBaseTableModel {

	/**
	 * 
	 */
	public TableModelTerreni() {
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
		Verticale curVe = man.getCurrentVerticale();
                
                if ( curVe == null ) {
			return new Double[ 0 ][7];
		}		
                
                
                ArrayList terreni = curVe.getStrati();

                if ( terreni == null ) {
			return new Double[ 0 ][7];
		}		
				
		Object[][] rowData = 
			new Object[ terreni.size()][7];
		
		int id;
		for (id=0; id < terreni.size();++id){
			Terreno ter  = (Terreno)terreni.get(id);
			rowData[id][0] = id;
			rowData[id][1] = new Double( ter.getH());
			rowData[id][2] = new Double(  ter.getFi() );
			rowData[id][3] = new Double(  ter.getGamma() );
			rowData[id][4] = new Double(  ter.getC() );
			rowData[id][5] = new Double(  ter.getKa() );
			rowData[id][6] = new Double(  ter.getAlfa() );
			
		}
		
		return rowData;
	}
	
	public boolean isCellEditable(int row, int col) {
		if ( col == 0 ) return false;
		return true;
	}
        
        public Object[][] getRowsSelected() {
		return getRowsSelected();
	}
        
	/**
	 * 
	 * @return
	 */
	public String[] loadHeaders(){
		String[] headers = new String[7];
		headers[0]="strato num";
		headers[1]="Quota (m) da p.c.";
		headers[2]="θ";
		headers[3]="γ (kN/mc)";	
		headers[4]="Cu";
                headers[5]="K";
                headers[6]="α";
		return headers;
	}

}
