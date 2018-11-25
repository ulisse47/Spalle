/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;


import progetto.model.bean.SpallaManager;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelOutputforzeLaterali extends AbstractBaseTableModel {

    /**
     * 
     */
    public TableModelOutputforzeLaterali() {
        super();
        rowData = loadRowData();
        header = loadHeaders();
    }

    /**
     * 
     * @return
     */
    public Object[][] loadRowData() {

        SpallaManager man = SpallaManager.getInstance();
        double[][][] delta = man.getSPOST_NODI();
        double[][][] forze = man.getMEXT();
        double[][] press = man.getPRESS_NODI();
        double zBase = man.getCurrentSpalla().getZpalo();
        double lPalo = man.getCurrentSpalla().getLpalo();
        
        try {
            int nNodi = delta.length;
            int nElem = nNodi - 1;
            double z0 = zBase;
            double dh = lPalo/nElem; 
            Object[][] rowData = new Object[nNodi][5];
           
            for (int id = 0; id < nElem; ++id) {
                rowData[id][0] = new Double ((Math.ceil(z0*100))/100);
                rowData[id][1] = new Double ((Math.ceil(delta[id][1][0]*100000))/100);
                rowData[id][2] = new Double ((Math.ceil(forze[id][0][0]*100))/100);
                rowData[id][3] = new Double (0);
                rowData[id][4] = new Double ((Math.ceil(press[id][0]*100))/100);
                z0+=dh;
            }
               rowData[nElem][0] = new Double (0);
                rowData[nElem][1] = new Double (0);
                rowData[nElem][2] = new Double (0);
                rowData[nElem][3] = new Double (0);
                rowData[nElem][4] = new Double (0);
            
                return rowData;
                
        } catch (Exception exception) {
            return new Double[0][5];
        }

 //       return rowData;
    }

    public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return false;
        }
        return true;
    }

    public Object[][] getRowsSelected() {
        return getRowsSelected();
    }

    /**
     * 
     * @return
     */
    public String[] loadHeaders() {
        String[] headers = new String[5];
        headers[0] = "H m";
        headers[1] = "δ (mm)";
        headers[2] = "M (kNm)";
        headers[3] = "V (kN)";
        headers[4] = "pressione  (kPa)";
        return headers;
    }
}
