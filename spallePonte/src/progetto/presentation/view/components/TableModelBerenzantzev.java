/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import progetto.model.bean.Berezantzev;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelBerenzantzev extends AbstractBaseTableModel {

    /**
     * 
     */
    public TableModelBerenzantzev() {
        super();
        rowData = loadRowData();
        header = loadHeaders();
    }

    /**
     * 
     * @return
     */
    public Object[][] loadRowData() {
//		SpallaManager man = SpallaManager.getInstance();
//		Verticale curVe = man.getCurrentVerticale();

        Object[][] rowData =
                new Object[21][5];
        double[][] ber = new Berezantzev().getRow();

        for (int i = 0; i < 21; ++i) {
            for (int j = 0; j < 5; ++j) {
                rowData[i][j] = new Double(ber[i][j]);
            }
        }

        return rowData;
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
        headers[0] = "θ (deg)";
        headers[1] = "L/D = 5";
        headers[2] = "L/D = 10";
        headers[3] = "L/D = 20";
        headers[4] = "L/D = 50";
        return headers;
    }
}
