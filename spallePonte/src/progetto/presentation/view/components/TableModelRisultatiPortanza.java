/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import progetto.model.bean.Terreno;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.Verticale;
import progetto.model.util.Calcoli;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableModelRisultatiPortanza extends AbstractBaseTableModel {

    /**
     * 
     */
    public TableModelRisultatiPortanza() {
        super();
        rowData = loadRowData();
        header = loadHeaders();
    }

    /**
     * 
     * @return
     */
    public Object[][] loadRowData() {
        SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
        SpallaManager man = SpallaManager.getInstance();
        ArrayList verticali = man.getVerticaliIndagate();
        
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        int nvert = verticali.size();

        if (nvert == 0) {
            return new Double[0][4];
        }


        Object[][] rowData = new Object[30 + 2 * nvert][4];

        //Portate laterali singole verticali
        double Qlmin = Double.MAX_VALUE; //calcolo anceh valori min
        double Qlmed = 0; //calcolo anceh valore medio
        double Qli = 0;
        int c = 0;

        //portata laterale
        rowData[0][0] = new String("PORTATA LATERALE");
        rowData[0][1] = new String("");
        rowData[0][2] = new String("");
        rowData[0][3] = new String("");

        for (int i = 0; i < nvert; ++i) {
            Verticale vert = (Verticale) verticali.get(i);
            Qli = del.calcolaQl1Strati(vert);
            if (Qli < Qlmin) {
                Qlmin = Qli;
            }
            Qlmed += Qli;
            rowData[i + 1][0] = new String("Qlaterale: " + vert.getName());
            rowData[i + 1][1] = nf.format(Qli);
            rowData[i + 1][2] = nf.format(Qli);
            rowData[i + 1][3] = nf.format(Qli);
        }
        //valori medi e min
        Qlmed = Qlmed / nvert;
        c = 1;
        rowData[nvert + c][0] = new String("Qlmed (portata laterale media)");
        rowData[nvert + c][1] = nf.format(Qlmed);
        rowData[nvert + c][2] = nf.format(Qlmed);
        rowData[nvert + c][3] = nf.format(Qlmed);

        c++;
        rowData[nvert + c][0] = new String("Qlmin (portata laterale minima)");
        rowData[nvert + c][1] = nf.format(Qlmin);
        rowData[nvert + c][2] = nf.format(Qlmin);
        rowData[nvert + c][3] = nf.format(Qlmin);

        double csi3 = del.getCsi3();
        double csi4 = del.getCsi4();
        c++;
        rowData[nvert + c][0] = new String("Coeff. correlazione ξ3");
        rowData[nvert + c][1] = csi3;
        rowData[nvert + c][2] = csi3;
        rowData[nvert + c][3] = csi3;
        c++;
        rowData[nvert + c][0] = new String("Coeff. correlazione ξ4");
        rowData[nvert + c][1] = csi4;
        rowData[nvert + c][2] = csi4;
        rowData[nvert + c][3] = csi4;

        double Ql1 = Qlmed / csi3;
        double Ql2 = Qlmin / csi4;
        double Qlk = Math.min(Ql1, Ql2);
        c++;
        rowData[nvert + c][0] = new String("Qlmed/ξ3");
        rowData[nvert + c][1] = nf.format(Ql1);
        rowData[nvert + c][2] = nf.format(Ql1);
        rowData[nvert + c][3] = nf.format(Ql1);
        c++;
        rowData[nvert + c][0] = new String("Qlmin/ξ4");
        rowData[nvert + c][1] = nf.format(Ql2);
        rowData[nvert + c][2] = nf.format(Ql2);
        rowData[nvert + c][3] = nf.format(Ql2);
        c++;
        rowData[nvert + c][0] = new String("Qlk (portata laterale caratt.");
        rowData[nvert + c][1] = nf.format(Qlk);
        rowData[nvert + c][2] = nf.format(Qlk);
        rowData[nvert + c][3] = nf.format(Qlk);

        double gR1 = del.gammaR1_portataLaterale();
        double gR2 = del.gammaR2_portataLaterale();
        double gR3 = del.gammaR3_portataLaterale();
        c++;
        rowData[nvert + c][0] = new String("γRc (compressione)");
        rowData[nvert + c][1] = gR1;
        rowData[nvert + c][2] = gR2;
        rowData[nvert + c][3] = gR3;

        double gR1t = del.gammaR1_portataLateraleTraz();
        double gR2t = del.gammaR2_portataLateraleTraz();
        double gR3t = del.gammaR3_portataLateraleTraz();
        c++;
        rowData[nvert + c][0] = new String("γRt (trazione)");
        rowData[nvert + c][1] = nf.format(gR1t);
        rowData[nvert + c][2] = nf.format(gR2t);
        rowData[nvert + c][3] = nf.format(gR3t);
        c++;
        rowData[nvert + c][0] = new String("Qld (portata laterale progetto compr.)");
        rowData[nvert + c][1] = nf.format(Qlk / gR1);
        rowData[nvert + c][2] = nf.format(Qlk / gR2);
        rowData[nvert + c][3] = nf.format(Qlk / gR3);

        c++;
        rowData[nvert + c][0] = new String("Qld (portata laterale progetto traz.)");
        rowData[nvert + c][1] = nf.format(-Qlk / gR1t);
        rowData[nvert + c][2] = nf.format(-Qlk / gR2t);
        rowData[nvert + c][3] = nf.format(-Qlk / gR3t);

        //portata di base
        c++;
        rowData[nvert + c][0] = new String("PORTATA BASE");
        rowData[nvert + c][1] = new String("");
        rowData[nvert + c][2] = new String("");
        rowData[nvert + c][3] = new String("");
        //Portate laterali singole verticali
        double Qbmin = Double.MAX_VALUE; //calcolo anceh valori min
        double Qbmed = 0; //calcolo anceh valore medio
        double Qbi = 0;

        c++;
        for (int i = 0; i < nvert; ++i) {
            Verticale vert = (Verticale) verticali.get(i);
            Qbi = del.calcolaQBaseStrati(vert);
            if (Qbi < Qbmin) {
                Qbmin = Qbi;
            }
            Qbmed += Qbi;
            rowData[nvert + c + i][0] = new String("Qbase: " + vert.getName());
            rowData[nvert + c + i][1] = nf.format(Qbi);
            rowData[nvert + c + i][2] = nf.format(Qbi);
            rowData[nvert + c + i][3] = nf.format(Qbi);
        }

        //valori medi e min
        Qbmed = Qbmed / nvert;
        rowData[2 * nvert + c][0] = new String("Qbmed (portata base media)");
        rowData[2 * nvert + c][1] = nf.format(Qbmed);
        rowData[2 * nvert + c][2] = nf.format(Qbmed);
        rowData[2 * nvert + c][3] = nf.format(Qbmed);

        c++;
        rowData[2 * nvert + c][0] = new String("Qbmin (portata base media)");
        rowData[2 * nvert + c][1] = nf.format(Qbmin);
        rowData[2 * nvert + c][2] = nf.format(Qbmin);
        rowData[2 * nvert + c][3] = nf.format(Qbmin);

        c++;
        rowData[2 * nvert + c][0] = new String("Coeff. correlazione ξ3");
        rowData[2 * nvert + c][1] = csi3;
        rowData[2 * nvert + c][2] = csi3;
        rowData[2 * nvert + c][3] = csi3;
        c++;
        rowData[2 * nvert + c][0] = new String("Coeff. correlazione ξ4");
        rowData[2 * nvert + c][1] = csi4;
        rowData[2 * nvert + c][2] = csi4;
        rowData[2 * nvert + c][3] = csi4;

        double Qb1 = Qbmed / csi3;
        double Qb2 = Qbmin / csi4;
        double Qbk = Math.min(Qb1, Qb2);
        c++;
        rowData[2 * nvert + c][0] = new String("Qbmed/ξ3");
        rowData[2 * nvert + c][1] = nf.format(Qb1);
        rowData[2 * nvert + c][2] = nf.format(Qb1);
        rowData[2 * nvert + c][3] = nf.format(Qb1);
        c++;
        rowData[2 * nvert + c][0] = new String("Qbmin/ξ4");
        rowData[2 * nvert + c][1] = nf.format(Qb2);
        rowData[2 * nvert + c][2] = nf.format(Qb2);
        rowData[2 * nvert + c][3] = nf.format(Qb2);
        c++;
        rowData[2 * nvert + c][0] = new String("Qbk (portata base caratteristica)");
        rowData[2 * nvert + c][1] = nf.format(Qbk);
        rowData[2 * nvert + c][2] = nf.format(Qbk);
        rowData[2 * nvert + c][3] = nf.format(Qbk);

        double gR1b = del.gammaR1_portataBase();
        double gR2b = del.gammaR2_portataBase();
        double gR3b = del.gammaR3_portataBase();
        c++;
        rowData[2 * nvert + c][0] = new String("γR");
        rowData[2 * nvert + c][1] = gR1b;
        rowData[2 * nvert + c][2] = gR2b;
        rowData[2 * nvert + c][3] = gR3b;
        c++;
        rowData[2 * nvert + c][0] = new String("Qbd (portata base di progetto)");
        rowData[2 * nvert + c][1] = nf.format(Qbk / gR1b);
        rowData[2 * nvert + c][2] = nf.format(Qbk / gR2b);
        rowData[2 * nvert + c][3] = nf.format(Qbk / gR3b);

        //portata di TOTALE
        c++;
        rowData[2 * nvert + c][0] = new String("PORTATA TOTALE");
        rowData[2 * nvert + c][1] = new String("");
        rowData[2 * nvert + c][2] = new String("");
        rowData[2 * nvert + c][3] = new String("");

        c++;
        rowData[2 * nvert + c][0] = new String("Qtotd,comp (portata compressionetotale lorda)");
        rowData[2 * nvert + c][1] = nf.format(Qbk / gR1b + Qlk / gR1);
        rowData[2 * nvert + c][2] = nf.format(Qbk / gR2b + Qlk / gR2);
        rowData[2 * nvert + c][3] = nf.format(Qbk / gR3b + Qlk / gR3);

        c++;
        rowData[2 * nvert + c][0] = new String("Qtotd,traz (portata trazione lorda)");
        rowData[2 * nvert + c][1] = nf.format(-Qlk / gR1t);
        rowData[2 * nvert + c][2] = nf.format(-Qlk / gR2t);
        rowData[2 * nvert + c][3] = nf.format(-Qlk / gR3t);

          c++;
        double pp = del.calcolaPesoPalo(true);
        rowData[2*nvert + c][0] = new String("Peso proprio palo (netto sottospinta)");
        rowData[2*nvert + c][1] = nf.format(pp);
        rowData[2*nvert + c][2] = nf.format(pp);
        rowData[2*nvert + c][3] = nf.format(pp);

        c++;
        rowData[2 * nvert + c][0] = new String("Qtotd,comp (portata compressionetotale netta)");
        rowData[2 * nvert + c][1] = nf.format(Qbk / gR1b + Qlk / gR1 -pp);
        rowData[2 * nvert + c][2] = nf.format(Qbk / gR2b + Qlk / gR2-pp);
        rowData[2 * nvert + c][3] = nf.format(Qbk / gR3b + Qlk / gR3-pp);

        c++;
        rowData[2 * nvert + c][0] = new String("Qtotd,traz (portata trazione netta)");
        rowData[2 * nvert + c][1] = nf.format(-Qlk / gR1t -pp);
        rowData[2 * nvert + c][2] = nf.format(-Qlk / gR2t -pp);
        rowData[2 * nvert + c][3] = nf.format(-Qlk / gR3t -pp);

        

        return rowData;
    }

    public boolean isCellEditable(int row, int col) {
        return false;

    }

    /**
     * 
     * @return
     */
    public String[] loadHeaders() {
        String[] headers = new String[4];
        headers[0] = "Dati";
        headers[1] = "M1+R1 (kN)";
        headers[2] = "M1+R2 (kN)";
        headers[3] = "M1+R3 (kN)";
        return headers;
    }
}
