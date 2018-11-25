package progetto.presentation.businessDelegate.stampa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import progetto.model.bean.Appoggio;
import progetto.model.bean.Carico;
import progetto.model.bean.CaricoForze;
import progetto.model.bean.Combinazione;
import progetto.model.bean.Palo;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.util.MyBeanUtils;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import progetto.model.bean.Terreno;
import progetto.model.bean.Verticale;

public class RTFCreator {

    /**
     * il titolo di ogni tabella
     */
    /*
     * private static Font fontTitleTable = new Font(Font.HELVETICA, 12,
     * Font.NORMAL);
     */
    private static Font cellHeaderFont = new Font(Font.HELVETICA, 8, Font.BOLD);
    private static Font cellFont = new Font(Font.HELVETICA, 8);
    private static Font titlePageFont = new Font(Font.HELVETICA, 12, Font.BOLD);
    private static Font titleTableFont = new Font(Font.HELVETICA, 10);
    private static NumberFormat nf;

    static {
        nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
    }
    public static String NEW_LINE = "\n";

    /**
     * @param document
     * @throws java.lang.Exception
     */
    private static void writeTable(Document document, String title,
            Hashtable input) throws Exception {

        int col = 2;
        int rows = input.size();

        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();

        Table table = new Table(col, rows + 1);
        // table.setPadding(2);
        // table.setSpacing(2);
        table.setDefaultHorizontalAlignment(Element.ALIGN_RIGHT);

        nf.setMinimumFractionDigits(2);

        Iterator iter = input.keySet().iterator();

        // titolo
        Phrase p = new Phrase(title, cellHeaderFont);
        Cell cella = new Cell(p);
        cella.setHorizontalAlignment(Element.ALIGN_LEFT);
        // cella.setHeader(true);
        table.addCell(cella, 0, 0);
        cella = new Cell("");
        cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cella, 0, 1);

        int i = 1;
        while (iter.hasNext()) {

            // for (int i = 0; i < rows; i++) {
            String key = (String) iter.next();
            String text = (String) input.get(key);

            Number d = (Number) MyBeanUtils.getProperty(spalla, key);

            String value1 = nf.format(d.doubleValue());
            /* Cell */ cella = new Cell(new Phrase(text, cellFont));
            cella.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cella, i, 0);
            cella = new Cell(new Phrase(value1, cellFont));
            cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cella, i, 1);
            i++;
        }

        document.add(table);
    }

    public static Hashtable createTabellaDaFrame(String propFile)
            throws IOException {


        //prop.load( RTFCreator.class.getResourceAsStream( "/cassone/model/resources/test.properties" ) );

        Properties input = new Properties();
        input.load(RTFCreator.class.getResourceAsStream(propFile));

        return input;
    }

    public static void writeSolIncastroSingoliCarichi(Document document)
            throws DocumentException {
        String[] headers = {"Azione", "Fx(kN)", "Fy(kN)", "Fz(kN)", "Mx(kNm)",
            "My(kNm)"
        };
        DataTable redditi = new DataTable();

        // popolo la tabella
        // ArrayList<Combinazione> combos = SpallaManager.getInstance()
        // .getCurrentSpalla().getCombinazioni();
        SpalleBusinessDelegateImpl del = (SpalleBusinessDelegateImpl) SpalleBusinessDelegateImpl.getInstance();

        // elevazioni
        Vector<String> vect = new Vector<String>();
        vect.add("Peso P.: Elevazioni");
        for (int j = 0; j < 5; ++j) {
            vect.add(Double.toString(del.getM1Spalla()[j]));
        }
        redditi.addRow(vect);

        // paraghiaia
        Vector<String> vect2 = new Vector<String>();
        vect2.add("Peso P.: Paraghiaia");
        for (int j = 0; j < 5; ++j) {
            vect2.add(Double.toString(del.getM1Paraghiaia()[j]));
        }
        redditi.addRow(vect2);

        // Totali permanenti
        Vector<String> vectP = new Vector<String>();
        vectP.add("TOT PESI PROPRI");
        for (int j = 0; j < 5; ++j) {
            vectP.add(Double.toString(del.getM1Permanenti()[j]));
        }
        redditi.addRow(vectP);

        // Sovraccarico q H
        Vector<String> vect3 = new Vector<String>();
        vect3.add("Spinta sovraccarico q (statica)");
        for (int j = 0; j < 5; ++j) {
            vect3.add(Double.toString(del.getM1Sovraccarico(false)[j]));
        }
        redditi.addRow(vect3);

        // Spinta terreno statica
        Vector<String> vect5 = new Vector<String>();
        vect5.add("Spinta terreno a tergo (cond. statiche)");
        for (int j = 0; j < 5; ++j) {
            vect5.add(Double.toString(del.getM1SpintaStaticaH(false)[j]));
        }
        redditi.addRow(vect5);


        // Sovraccarico durante sisma
        Vector<String> vect4 = new Vector<String>();
        vect4.add("Spinta sovraccarico q (sismica)");
        for (int j = 0; j < 5; ++j) {
            vect4.add(Double.toString(del.getM1Sovraccarico(true)[j]));
        }
        redditi.addRow(vect4);

        // Spinta statica (durante sisma)
        Vector<String> vect6 = new Vector<String>();
        vect6.add("Spinta terreno a tergo (cond. sismiche)");
        for (int j = 0; j < 5; ++j) {
            vect6.add(Double.toString(del.getM1SpintaStaticaH(true)[j]));
        }
        redditi.addRow(vect6);

        // spinte dinamiche
        Vector<String> vect8 = new Vector<String>();
        vect8.add("Forze inerzia sismiche 'Sisma x'");
        for (int j = 0; j < 5; ++j) {
            vect8.add(Double.toString(del.getM1SpinteDianamicheSismaX()[j]));
        }
        redditi.addRow(vect8);

        // spinte dinamiche y
        Vector<String> vect9 = new Vector<String>();
        vect9.add("Forze inerzia sismiche 'Sisma y'");
        for (int j = 0; j < 5; ++j) {
            vect9.add(Double.toString(del.getM1SpinteDianamicheSismaY()[j]));
        }
        redditi.addRow(vect9);

        // Carichi appoggi
        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();
        ArrayList<Carico> carichi = spalla.getCarichi();
        for (int i = 0; i < carichi.size(); i++) {
            Vector<String> vectSc = new Vector<String>();
            vectSc.add(carichi.get(i).getName());
            for (int j = 0; j < 5; ++j) {
                vectSc.add(Double.toString(del.getM1CaricoAppoggi(i)[j]));
            }
            redditi.addRow(vectSc);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);
        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeComboMensolaValle(Document document)
            throws DocumentException {
        String[] headers = {"Combo", "Mx(kNm)", "Vz(kN)", "mx(kNm/ml)",
            "vz(kN/ml)"
        };
        DataTable redditi = new DataTable();

        // popolo la tabella
        ArrayList<Combinazione> combos = SpallaManager.getInstance().getCurrentSpalla().getCombinazioni();
        SpalleBusinessDelegateImpl del = (SpalleBusinessDelegateImpl) SpalleBusinessDelegateImpl.getInstance();

        for (int i = 0; i < combos.size(); ++i) {
            Vector<String> vect = new Vector<String>();
            vect.add(combos.get(i).getName());
            vect.add(Double.toString(del.getMMensole(i)[2][0]));
            vect.add(Double.toString(del.getMMensole(i)[3][0]));
            vect.add(Double.toString(del.getMMensole(i)[2][1]));
            vect.add(Double.toString(del.getMMensole(i)[3][1]));
            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);
        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeComboMensolaMonte(Document document)
            throws DocumentException {
        String[] headers = {"Combo", "Mx(kNm)", "Vz(kN)", "mx(kNm/ml)",
            "vz(kN/ml)"
        };
        DataTable redditi = new DataTable();

        // popolo la tabella
        ArrayList<Combinazione> combos = SpallaManager.getInstance().getCurrentSpalla().getCombinazioni();
        SpalleBusinessDelegateImpl del = (SpalleBusinessDelegateImpl) SpalleBusinessDelegateImpl.getInstance();

        for (int i = 0; i < combos.size(); ++i) {
            Vector<String> vect = new Vector<String>();
            vect.add(combos.get(i).getName());
            vect.add(Double.toString(del.getMMensole(i)[0][0]));
            vect.add(Double.toString(del.getMMensole(i)[1][0]));
            vect.add(Double.toString(del.getMMensole(i)[0][1]));
            vect.add(Double.toString(del.getMMensole(i)[1][1]));
            redditi.addRow(vect);
        }


        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);
        Table datatable = tIcef.createTable();
        document.add(datatable);
    }

    public static void writeSolIntraSingoliCarichi(Document document)
            throws DocumentException {
        String[] headers = {"Azione", "Fx(kN)", "Fy(kN)", "Fz(kN)", "Mx(kNm)",
            "My(kNm)"
        };
        DataTable redditi = new DataTable();

        SpalleBusinessDelegateImpl del = (SpalleBusinessDelegateImpl) SpalleBusinessDelegateImpl.getInstance();


        // ciabatta di fondazione
        Vector<String> vectCiab = new Vector<String>();
        vectCiab.add("P.P.: Platea");
        for (int j = 0; j < 5; ++j) {
            vectCiab.add(Double.toString(del.getM2Ciabatta()[j]));
        }
        redditi.addRow(vectCiab);


        // Muri ala
        Vector<String> vectMa = new Vector<String>();
        vectMa.add("P.P.: Muri d'ala");
        for (int j = 0; j < 5; ++j) {
            vectMa.add(Double.toString(del.getM2MuriAla()[j]));
        }
        redditi.addRow(vectMa);

        // Terreno di monte
        Vector<String> vectTm = new Vector<String>();
        vectTm.add("P.P.: Peso terreno di monte");
        for (int j = 0; j < 5; ++j) {
            vectTm.add(Double.toString(del.getM2TerrenoMonteV()[j]));
        }
        redditi.addRow(vectTm);

        // Terreno di valle
        Vector<String> vectTv = new Vector<String>();
        vectTv.add("P.P.: Peso terreno di valle");
        for (int j = 0; j < 5; ++j) {
            vectTv.add(Double.toString(del.getM2TerrenoValle()[j]));
        }
        redditi.addRow(vectTv);

        // elevazioni
        Vector<String> vect = new Vector<String>();
        vect.add("P.P.: Elevazioni");
        for (int j = 0; j < 5; ++j) {
            vect.add(Double.toString(del.getM2Spalla()[j]));
        }
        redditi.addRow(vect);

        // paraghiaia
        Vector<String> vect2 = new Vector<String>();
        vect2.add("P.P.: Paraghiaia");
        for (int j = 0; j < 5; ++j) {
            vect2.add(Double.toString(del.getM2Paraghiaia()[j]));
        }
        redditi.addRow(vect2);

        // Totali permanenti
        Vector<String> vectP = new Vector<String>();
        vectP.add("PESI P.: TOTALI");
        for (int j = 0; j < 5; ++j) {
            vectP.add(Double.toString(del.getM2Permanenti()[j]));
        }
        redditi.addRow(vectP);

        // Sovraccarico verticale
        Vector<String> vect3 = new Vector<String>();
        vect3.add("Sovraccarico q (azione verticale)");
        for (int j = 0; j < 5; ++j) {
            vect3.add(Double.toString(del.getM2SovraccaricoV()[j]));
        }
        redditi.addRow(vect3);

        // Sovraccarico orizzontale (condizioni statiche)
        Vector<String> vect4 = new Vector<String>();
        vect4.add("Sovraccarico q spinta orizzontale (condizioni statiche)");
        for (int j = 0; j < 5; ++j) {
            vect4.add(Double.toString(del.getM2SovraccaricoH(false)[j]));
        }
        redditi.addRow(vect4);

        // Sovraccarico orizzontale (condizioni sismiche)
        Vector<String> vect4b = new Vector<String>();
        vect4b.add("Sovraccarico q spinta orizzontale (condizioni sismiche)");
        for (int j = 0; j < 5; ++j) {
            vect4b.add(Double.toString(del.getM2SovraccaricoH(true)[j]));
        }
        redditi.addRow(vect4b);


        // Spinta statica
        Vector<String> vect5 = new Vector<String>();
        vect5.add("Spinta terreno a tergo (condizioni statiche)");
        for (int j = 0; j < 5; ++j) {
            vect5.add(Double.toString(del.getM2SpintaStatica(false)[j]));
        }
        redditi.addRow(vect5);

        // Spinta statica (durante sisma)
        Vector<String> vect6 = new Vector<String>();
        vect6.add("Spinta terreno a tergo (condizioni sismiche)");
        for (int j = 0; j < 5; ++j) {
            vect6.add(Double.toString(del.getM2SpintaStatica(true)[j]));
        }
        redditi.addRow(vect6);

        // sisma x
        Vector<String> vect8 = new Vector<String>();
        vect8.add("Forze di inerzia 'Sisma x'");
        for (int j = 0; j < 5; ++j) {
            vect8.add(Double.toString(del.getM2SpinteDianamicheSismaX()[j]));
        }
        redditi.addRow(vect8);

        // sisma y
        Vector<String> vect9 = new Vector<String>();
        vect9.add("Forze di inerzia 'Sisma y'");
        for (int j = 0; j < 5; ++j) {
            vect9.add(Double.toString(del.getM2SpinteDianamicheSismaY()[j]));
        }
        redditi.addRow(vect9);

        // Carichi appoggi
        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();
        ArrayList<Carico> carichi = spalla.getCarichi();
        for (int i = 0; i < carichi.size(); i++) {
            Vector<String> vectSc = new Vector<String>();
            vectSc.add(carichi.get(i).getName());
            for (int j = 0; j < 5; ++j) {
                vectSc.add(Double.toString(del.getM2CaricoAppoggi(i)[j]));
            }
            redditi.addRow(vectSc);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeSolIncastro(Document document)
            throws DocumentException {

        String[] headers = {"combo", "Fx(kN)", "Fy(kN)", "Fz(kN)", "Mx(kNm)",
            "My(kNm)"
        };
        DataTable redditi = new DataTable();

        // popolo la tabella
        ArrayList<Combinazione> combos = SpallaManager.getInstance().getCurrentSpalla().getCombinazioni();
        SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();

        for (int i = 0; i < combos.size(); i++) {
            Combinazione combo = combos.get(i);

            Vector<String> vect = new Vector<String>();
            vect.add(combo.getName());

            vect.add(Double.toString(del.getM1Combo(i)[0]));
            vect.add(Double.toString(del.getM1Combo(i)[1]));
            vect.add(Double.toString(del.getM1Combo(i)[2]));
            vect.add(Double.toString(del.getM1Combo(i)[3]));
            vect.add(Double.toString(del.getM1Combo(i)[4]));

            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        // float[] w = { 3, 5, 3, 10 };
		/*
         * int[] al = { Element.ALIGN_CENTER, Element.ALIGN_CENTER,
         * Element.ALIGN_CENTER, Element.ALIGN_CENTER };
         */
        // tIcef.setWidths( w );
        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        // tIcef.setColumFont( FontFactory.getFont(FontFactory.HELVETICA, 8), 4
        // );

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeSolIntra(Document document)
            throws DocumentException {

        String[] headers = {"combo", "Fx(kN)", "Fy(kN)", "Fz(kN)", "Mx(kN)",
            "My(kN)"
        };
        DataTable redditi = new DataTable();

        // popolo la tabella
        ArrayList<Combinazione> combos = SpallaManager.getInstance().getCurrentSpalla().getCombinazioni();
        SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();

        for (int i = 0; i < combos.size(); i++) {
            Combinazione combo = combos.get(i);

            Vector<String> vect = new Vector<String>();
            vect.add(combo.getName());

            vect.add(Double.toString(del.getM2Combo(i)[0]));
            vect.add(Double.toString(del.getM2Combo(i)[1]));
            vect.add(Double.toString(del.getM2Combo(i)[2]));
            vect.add(Double.toString(del.getM2Combo(i)[3]));
            vect.add(Double.toString(del.getM2Combo(i)[4]));

            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        // float[] w = { 3, 5, 3, 10 };
		/*
         * int[] al = { Element.ALIGN_CENTER, Element.ALIGN_CENTER,
         * Element.ALIGN_CENTER, Element.ALIGN_CENTER };
         */
        // tIcef.setWidths( w );
        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        // tIcef.setColumFont( FontFactory.getFont(FontFactory.HELVETICA, 8), 4
        // );

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeAppoggi(Document document) throws DocumentException {

        String[] headers = {"Appoggio", "x(m)", "y(m)", "z(m)"};
        DataTable redditi = new DataTable();

        // popolo la tabella
        ArrayList<Appoggio> appoggi = SpallaManager.getInstance().getCurrentSpalla().getAppoggi();

        for (int i = 0; i < appoggi.size(); i++) {
            Appoggio appoggio = appoggi.get(i);
            Vector<String> vect = new Vector<String>();
            vect.add(appoggio.getName());
            vect.add(Double.toString(appoggio.getX()));
            vect.add(Double.toString(appoggio.getY()));
            vect.add(Double.toString(appoggio.getZ()));

            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        // float[] w = { 3, 5, 3, 10 };
        int[] al = {Element.ALIGN_CENTER, Element.ALIGN_CENTER,
            Element.ALIGN_CENTER, Element.ALIGN_CENTER
        };
        // tIcef.setWidths( w );
        tIcef.setCellAllignment(al);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        // tIcef.setColumFont( FontFactory.getFont(FontFactory.HELVETICA, 8), 4
        // );

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeGeomPali(Document document)
            throws DocumentException {

        String[] headers = {"Palo N°", "x(m)", "y(m)", "diametro(m)"};
        DataTable redditi = new DataTable();

        // popolo la tabella
        ArrayList<Palo> pali = SpallaManager.getInstance().getCurrentSpalla().getPalificata();

        for (int i = 0; i < pali.size(); i++) {
            Palo palo = pali.get(i);
            Vector<String> vect = new Vector<String>();
            vect.add(Integer.toString(i + 1));
            vect.add(Double.toString(palo.getX()));
            vect.add(Double.toString(palo.getY()));
            vect.add(Double.toString(palo.getDiametro()));

            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        // float[] w = { 3, 5, 3, 10 };
        int[] al = {Element.ALIGN_CENTER, Element.ALIGN_CENTER,
            Element.ALIGN_CENTER, Element.ALIGN_CENTER
        };
        // tIcef.setWidths( w );
        tIcef.setCellAllignment(al);

        int[] data_type = {PdfUtil.INTEGER, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        // tIcef.setColumFont( FontFactory.getFont(FontFactory.HELVETICA, 8), 4
        // );

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeCarico(Document document, Carico carico,
            ArrayList appoggi) throws DocumentException {

        String[] headers = {"Appoggio", "Fx(kN)", "Fy(kN)", "Fz(kN)",
            "Mx(kNm)", "My(kNm)"
        };
        DataTable redditi = new DataTable();

        // popolo la tabella
        ArrayList<CaricoForze> forze = carico.getForzeAppoggi();

        for (int i = 0; i < forze.size(); i++) {
            Appoggio ap = (Appoggio) appoggi.get(i);
            Vector<String> vect = new Vector<String>();
            vect.add(ap.getName());
            if (!carico.isAtrito()) {
                CaricoForze car = forze.get(i);
                vect.add(Double.toString(car.getFx()));
                vect.add(Double.toString(car.getFy()));
                vect.add(Double.toString(car.getFz()));
                vect.add(Double.toString(car.getMx()));
                vect.add(Double.toString(car.getMy()));
            } else {
                SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
                double[] M = del.getFyCaricoAppoggiAtrito();
                vect.add(Double.toString(0));
                vect.add(Double.toString(M[i]));
                vect.add(Double.toString(0));
                vect.add(Double.toString(0));
                vect.add(Double.toString(0));

            }
            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeCombinazioni(Document document)
            throws DocumentException {

        DataTable redditi = new DataTable();

        // popolo la tabella
        Spalla spalla = SpallaManager.getInstance().getCurrentSpalla();

        ArrayList<Combinazione> combo = spalla.getCombinazioni();
        ArrayList<Carico> carichi = spalla.getCarichi();
        int size = carichi.size();

        String[] headers = new String[size + 7];
        headers[0] = "Combo";
        headers[1] = "Pesi propri";
        headers[2] = "Sovracc. q";
        headers[3] = "Spinte orizzontali statiche";
        headers[4] = "Spinte orizzontali statiche";
        headers[5] = "Inerzie sismiche X";
        headers[6] = "Inerzie sismiche Y";

        for (int i = 7; i < size + 7; i++) {
            headers[i] = carichi.get(i - 7).getName();
        //headers[i] = "a" + Integer.toString(i - 5);
        }

        for (int i = 0; i < combo.size(); i++) {
            Combinazione car = combo.get(i);
            Vector<String> vect = new Vector<String>();
            vect.add(car.getName());
            ArrayList<Double> combos = car.getCombo();
            for (int z = 0; z < size + 5; z++) {
                vect.add(Double.toString(combos.get(z)));
            }
            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        /*
         * int[] data_type = { PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
         * PdfUtil.EURO };
         */

        int[] data_type = new int[size + 7];
        data_type[0] = PdfUtil.TEXT;
        data_type[1] = PdfUtil.EURO;
        data_type[2] = PdfUtil.EURO;
        data_type[3] = PdfUtil.EURO;
        data_type[4] = PdfUtil.EURO;
        data_type[5] = PdfUtil.EURO;
        data_type[6] = PdfUtil.EURO;
        for (int i = 7; i < size + 7; i++) {
            data_type[i] = PdfUtil.EURO;
        }

        tIcef.setColumnDataType(data_type);

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeComboPali(Document document)
            throws DocumentException {

        DataTable redditi = new DataTable();

        // popolo la tabella
        Spalla spalla = SpallaManager.getInstance().getCurrentSpalla();
        SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();

        ArrayList<Combinazione> combo = spalla.getCombinazioni();
        ArrayList<Palo> pali = SpallaManager.getInstance().getCurrentSpalla().getPalificata();

        String[] headers = new String[combo.size() + 1];
        headers[0] = "Palo N°";
        for (int i = 1; i < combo.size() + 1; i++) {
            headers[i] = combo.get(i - 1).getName();
        }

        for (int i = 0; i < pali.size(); i++) {

            Vector<String> vect = new Vector<String>();

            vect.add(Integer.toString(i + 1));

            for (int z = 0; z < combo.size(); z++) {
                vect.add(Integer.toString((int) del.getNpalificata(z)[i]));
            }
            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        /*
         * int[] data_type = { PdfUtil.TEXT, PdfUtil.EURO, PdfUtil.EURO,
         * PdfUtil.EURO };
         */

        int[] data_type = new int[combo.size() + 1];
        data_type[0] = PdfUtil.INTEGER;
        for (int i = 1; i < combo.size() + 1; i++) {
            data_type[i] = PdfUtil.INTEGER;
        }

        tIcef.setColumnDataType(data_type);

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static Document creaRTFDocument(String fileName) throws Exception {
        // step 1: creation of a document-object
        Document document = new Document();

        RtfWriter2.getInstance(document, new FileOutputStream(fileName));
        // PdfWriter.getInstance(document, new FileOutputStream(fileName));

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        document.open();

        document.setMargins(40, 40, 40, 40);

        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();

        Paragraph par1 = new Paragraph("PROGETTO: " + spalla.getNome(),
                titlePageFont);
        document.add(par1);

        document.add(new Phrase(NEW_LINE));

        Paragraph par = new Paragraph("1. DATI DI INPUT", titleTableFont);
        document.add(par);
        document.add(new Phrase(NEW_LINE));

        writeTable(document, "Pesi Specifici",
                createTabellaDaFrame("/progetto/presentation/businessDelegate/stampa/pesi.properties"));
        writeTable(document, "Terreno",
                createTabellaDaFrame("/progetto/presentation/businessDelegate/stampa/spinta.properties"));
        writeTable(document, "Fondazioni",
                createTabellaDaFrame("/progetto/presentation/businessDelegate/stampa/fondazione.properties"));
        writeTable(document, "Elevazioni",
                createTabellaDaFrame("/progetto/presentation/businessDelegate/stampa/elevazioni.properties"));
        writeTable(document, "Paraghiaia",
                createTabellaDaFrame("/progetto/presentation/businessDelegate/stampa/para.properties"));
        writeTable(document, "Muri d'ala",
                createTabellaDaFrame("/progetto/presentation/businessDelegate/stampa/muri.properties"));
        writeTable(document, "Azioni sismiche",
                createTabellaDaFrame("/progetto/presentation/businessDelegate/stampa/sismica.properties"));

        // APPOGGI
        writeTableTitle(
                document,
                "2. COORDINATE APPOGGI (x e y da baricentro elevazioni, z da estradosso fondazioni)");
        writeAppoggi(document);

        // CARICHI
        ArrayList<Carico> carichi = spalla.getCarichi();
        ArrayList<Appoggio> appoggi = SpallaManager.getInstance().getCurrentSpalla().getAppoggi();
        writeTableTitle(document, "3. AZIONE TRASMESSE SUGLI APPOGGI");
        for (int i = 0; i < carichi.size(); i++) {
            writeTableTitle(document, "a." + Integer.toString(i + 1) + ". " + carichi.get(i).getName());
            writeCarico(document, carichi.get(i), appoggi);
        }

        writeTableTitle(document, "4. COMBINAZIONI");
        writeCombinazioni(document);

        writeTableTitle(document,
                "5. RIEPILOGO AZIONI ZONA INCASTRO ELEVAZIONI");
        writeSolIncastroSingoliCarichi(document);

        writeTableTitle(document,
                "6. RIEPILOGO AZIONI ZONA INTRADOSSO FONDAZIONI");
        writeSolIntraSingoliCarichi(document);

        writeTableTitle(
                document,
                "7. AZIONI COMBINATE ZONA INCASTRO (valori relativi ad assi locali elevazioni x',y')");
        writeSolIncastro(document);

        writeTableTitle(
                document,
                "8. AZIONI COMBINATE INTRADOSSO FONDAZIONE (valori relativi ad assi principali x,y)");
        writeSolIntra(document);

        writeTableTitle(document,
                "9. AZIONI COMBINATE APPOGGI (valori relativi ad assi principali x,y)");
        writeSolAppoggi(document);

        if (spalla.getPalificata().size() != 0) {

            writeTableTitle(document, "10. GEOMETRIA PALIFICATA");
            writeGeomPali(document);

            writeTableTitle(document,
                    "11. AZIONI VERTICALI (in kN) SUI SINGOLI PALI NELLE DIVERSE COMBINAZIONI");
            writeComboPali(document);

            writeTableTitle(document, "12. SOLLECITAZIONI MENSOLA DI VALLE");
            writeComboMensolaValle(document);

            writeTableTitle(document, "13. SOLLECITAZIONI MENSOLA DI MONTE");
            writeComboMensolaMonte(document);

        }

        ArrayList verticali = spalla.getVerticaliIndagate();
        int nver = verticali.size();
        if (nver != 0) {
            writeTableTitle(document, "14. CALCOLO PORTANZA SINGOLO PALO: dati generali");
            writeDatiPalo(document);

            writeTableTitle(document, "15. VERTICALI INDAGATE");
            for (int i = 0; i < nver; ++i) {
                Verticale ver = (Verticale) verticali.get(i);
                Paragraph par2 = new Paragraph("Verticale: '" + ver.getName(),
                        cellHeaderFont);
                document.add(par2);
                writeVerticale(document, (Verticale) verticali.get(i));
            }

            writeTableTitle(document, "16. RISULTATI DI CALCOLO");
            writeTableRisultatiPortanza(document);


        }


        document.close();

        return document;
    }

    private static void writeSolAppoggi(Document document)
            throws DocumentException {

        SpalleBusinessDelegateImpl del = (SpalleBusinessDelegateImpl) SpalleBusinessDelegateImpl.getInstance();
        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();

        String[] headers = {"COMBO", "APPOGGIO", "Fx(kN)", "Fy(kN)", "Fz(kN)"};
        DataTable solAppoggi = new DataTable();

        ArrayList<Combinazione> combo = spalla.getCombinazioni();
        ArrayList<Appoggio> appoggi = SpallaManager.getInstance().getCurrentSpalla().getAppoggi();

        Vector<String> vect = new Vector<String>();
        double[][] m1;
        for (int j = 0; j < combo.size(); ++j) {
            String comboName = combo.get(j).getName();
            m1 = del.getMAppoggiCombo(j);
            for (int i = 0; i < appoggi.size(); i++) {
                vect = new Vector<String>();
                vect.add(comboName);
                vect.add(appoggi.get(i).getName());
                vect.add(Double.toString(m1[i][0]));
                vect.add(Double.toString(m1[i][1]));
                vect.add(Double.toString(m1[i][2]));
                solAppoggi.addRow(vect);
            }

            m1 = del.getMAppoggiPermanentiCombo(j);
            for (int i = 0; i < appoggi.size(); i++) {
                vect = new Vector<String>();
                vect.add(comboName + " (Perm)");
                vect.add(appoggi.get(i).getName());
                vect.add(Double.toString(m1[i][0]));
                vect.add(Double.toString(m1[i][1]));
                vect.add(Double.toString(m1[i][2]));
                solAppoggi.addRow(vect);
            }
        }

        SpalleTable tIcef = new SpalleTable(solAppoggi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        tIcef.setCellAllignment(Element.ALIGN_CENTER);

        int[] data_type = {PdfUtil.TEXT, PdfUtil.TEXT, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        Table datatable = tIcef.createTable();
        document.add(datatable);

    }

    public static void writeTableTitle(Document document, String title)
            throws DocumentException {
        document.add(new Phrase(NEW_LINE));
        Paragraph ph = new Paragraph(title, titleTableFont);
        ph.setAlignment(Element.ALIGN_LEFT);
        // ph.setLeading( 0 );
        document.add(ph);
    }

    private static void writeVerticale(Document document, Verticale verticale) throws DocumentException {

        String[] headers = {"num starto", "quota da p.c.(m)", "θ (deg)", "γ (kN/mc)", "cu", "K", "α"};
        DataTable redditi = new DataTable();

        // popolo la tabella
        ArrayList strati = verticale.getStrati();

        for (int i = 0; i < strati.size(); i++) {
            Terreno t = (Terreno) strati.get(i);
            Vector<String> vect = new Vector<String>();
            vect.add(Integer.toString(i));
            vect.add(Double.toString(t.getH()));
            vect.add(Double.toString(t.getFi()));
            vect.add(Double.toString(t.getGamma()));
            vect.add(Double.toString(t.getC()));
            vect.add(Double.toString(t.getKa()));
            vect.add(Double.toString(t.getAlfa()));

            redditi.addRow(vect);
        }

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        // float[] w = { 3, 5, 3, 10 };
        int[] al = {Element.ALIGN_CENTER, Element.ALIGN_CENTER,
            Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER,
            Element.ALIGN_CENTER, Element.ALIGN_CENTER
        };
        // tIcef.setWidths( w );
        tIcef.setCellAllignment(al);

        int[] data_type = {PdfUtil.INTEGER, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        // tIcef.setColumFont( FontFactory.getFont(FontFactory.HELVETICA, 8), 4
        // );

        Table datatable = tIcef.createTable();
        document.add(datatable);
    }

    private static void writeDatiPalo(Document document)
            throws DocumentException {

        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();

        String[] headers = {"Quota testa palo (m)", "Lunghezza palo (m)", "Diametro palo (m) ", "Quota falda (m)"};
        DataTable redditi = new DataTable();


        Vector<String> vect = new Vector<String>();

        vect.add(Double.toString(spalla.getZpalo()));
        vect.add(Double.toString(spalla.getLpalo()));
        vect.add(Double.toString(spalla.getDpalo()));
        vect.add(Double.toString(spalla.getZfalda()));

        redditi.addRow(vect);

        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);

        // tIcef.rows = 1;
        // float[] w = { 3, 5, 3, 10 };
        int[] al = {Element.ALIGN_CENTER, Element.ALIGN_CENTER,
            Element.ALIGN_CENTER, Element.ALIGN_CENTER
        };
        // tIcef.setWidths( w );
        tIcef.setCellAllignment(al);

        int[] data_type = {PdfUtil.EURO, PdfUtil.EURO, PdfUtil.EURO,
            PdfUtil.EURO
        };
        tIcef.setColumnDataType(data_type);

        // tIcef.setColumFont( FontFactory.getFont(FontFactory.HELVETICA, 8), 4
        // );

        Table datatable = tIcef.createTable();
        document.add(datatable);
    }

    private static void writeTableRisultatiPortanza(Document document)
            throws DocumentException {

        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();
        SpalleBusinessDelegateImpl del = (SpalleBusinessDelegateImpl) SpalleBusinessDelegateImpl.getInstance();

        ArrayList verticali = spalla.getVerticaliIndagate();
        int nvert = verticali.size();

        String[] headers = {"", "M1+R1", "M1+R2", "M1+R3"};
        DataTable redditi = new DataTable();

        Vector<String> vect = new Vector<String>();
        //Portate laterali singole verticali
        double Qlmin = Double.MAX_VALUE; //calcolo anceh valori min
        double Qlmed = 0; //calcolo anceh valore medio
        double Qli = 0;
        int c = 0;
        //portata laterale
        vect.add(new String("PORTATA LATERALE"));
        vect.add(new String(""));
        vect.add(new String(""));
        vect.add(new String(""));
        redditi.addRow(vect);

        for (int i = 0; i < nvert; ++i) {
            Verticale vert = (Verticale) verticali.get(i);
            Qli = del.calcolaQl1Strati(vert);
            if (Qli < Qlmin) {
                Qlmin = Qli;
            }
            Qlmed += Qli;
            vect = new Vector<String>();
            vect.add(new String("Qlaterale: " + vert.getName()));


//            vect.add(Double.toString(Qli));
            //           vect.add(Double.toString(Qli));
            //          vect.add(Double.toString(Qli));
            vect.add(nf.format(Qli));
            vect.add(nf.format(Qli));
            vect.add(nf.format(Qli));
            redditi.addRow(vect);
        }
        //valori medi e min
        Qlmed = Qlmed / nvert;
        vect = new Vector<String>();

        vect.add(new String("Qlmed (portata laterale media)"));
        vect.add(nf.format(Qlmed));
        vect.add(nf.format(Qlmed));
        vect.add(nf.format(Qlmed));
        redditi.addRow(vect);
        vect = new Vector<String>();

        vect.add(new String("Qlmin (portata laterale minima)"));
        vect.add(nf.format(Qlmin));
        vect.add(nf.format(Qlmin));
        vect.add(nf.format(Qlmin));
        redditi.addRow(vect);

        double csi3 = del.getCsi3();
        double csi4 = del.getCsi4();
        vect = new Vector<String>();
        vect.add(new String("Coeff. correlazione ξ3"));
        vect.add(nf.format(csi3));
        vect.add(nf.format(csi3));
        vect.add(nf.format(csi3));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Coeff. correlazione ξ4"));
        vect.add(nf.format(csi4));
        vect.add(nf.format(csi4));
        vect.add(nf.format(csi4));
        redditi.addRow(vect);

        double Ql1 = Qlmed / csi3;
        double Ql2 = Qlmin / csi4;
        double Qlk = Math.min(Ql1, Ql2);
        vect = new Vector<String>();
        vect.add(new String("Qlmed/ξ3"));
        vect.add(nf.format(Ql1));
        vect.add(nf.format(Ql1));
        vect.add(nf.format(Ql1));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qlmed/ξ4"));
        vect.add(nf.format(Ql2));
        vect.add(nf.format(Ql2));
        vect.add(nf.format(Ql2));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qlk (portata laterale caratt."));
        vect.add(nf.format(Qlk));
        vect.add(nf.format(Qlk));
        vect.add(nf.format(Qlk));
        redditi.addRow(vect);

        double gR1 = del.gammaR1_portataLaterale();
        double gR2 = del.gammaR2_portataLaterale();
        double gR3 = del.gammaR3_portataLaterale();
        vect = new Vector<String>();
        vect.add(new String("γRc (compressione)"));
        vect.add(nf.format(gR1));
        vect.add(nf.format(gR2));
        vect.add(nf.format(gR3));
        redditi.addRow(vect);

        double gR1t = del.gammaR1_portataLateraleTraz();
        double gR2t = del.gammaR2_portataLateraleTraz();
        double gR3t = del.gammaR3_portataLateraleTraz();
        vect = new Vector<String>();
        vect.add(new String("γRt (trazione)"));
        vect.add(nf.format(gR1t));
        vect.add(nf.format(gR2t));
        vect.add(nf.format(gR3t));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qld (portata laterale progetto compr.)"));
        vect.add(nf.format(Qlk / gR1));
        vect.add(nf.format(Qlk / gR2));
        vect.add(nf.format(Qlk / gR3));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qld (portata laterale progetto traz.)"));
        vect.add(nf.format(-Qlk / gR1t));
        vect.add(nf.format(-Qlk / gR2t));
        vect.add(nf.format(-Qlk / gR3t));
        redditi.addRow(vect);

        //portata di base
        vect = new Vector<String>();
        vect.add(new String("PORTATA BASE"));
        vect.add(new String(""));
        vect.add(new String(""));
        vect.add(new String(""));
        redditi.addRow(vect);

        //Portate base singole verticali
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
            vect = new Vector<String>();
            vect.add(new String("Qbase: " + vert.getName()));
            vect.add(nf.format(Qbi));
            vect.add(nf.format(Qbi));
            vect.add(nf.format(Qbi));
            redditi.addRow(vect);
        }
        //valori medi e min
        Qbmed = Qbmed / nvert;
        vect = new Vector<String>();
        vect.add(new String("Qbmed (portata base media)"));
        vect.add(nf.format(Qbmed));
        vect.add(nf.format(Qbmed));
        vect.add(nf.format(Qbmed));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qbmin (portata base media)"));
        vect.add(nf.format(Qbmin));
        vect.add(nf.format(Qbmin));
        vect.add(nf.format(Qbmin));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Coeff. correlazione ξ3"));
        vect.add(nf.format(csi3));
        vect.add(nf.format(csi3));
        vect.add(nf.format(csi3));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Coeff. correlazione ξ4"));
        vect.add(nf.format(csi4));
        vect.add(nf.format(csi4));
        vect.add(nf.format(csi4));
        redditi.addRow(vect);

        double Qb1 = Qbmed / csi3;
        double Qb2 = Qbmin / csi4;
        double Qbk = Math.min(Qb1, Qb2);
        vect = new Vector<String>();
        vect.add(new String("Qbmed/ξ3"));
        vect.add(nf.format(Qb1));
        vect.add(nf.format(Qb1));
        vect.add(nf.format(Qb1));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qbmin/ξ4"));
        vect.add(nf.format(Qb2));
        vect.add(nf.format(Qb2));
        vect.add(nf.format(Qb2));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qbk (portata base caratteristica)"));
        vect.add(nf.format(Qbk));
        vect.add(nf.format(Qbk));
        vect.add(nf.format(Qbk));
        redditi.addRow(vect);

        double gR1b = del.gammaR1_portataBase();
        double gR2b = del.gammaR2_portataBase();
        double gR3b = del.gammaR3_portataBase();

        vect = new Vector<String>();
        vect.add(new String("γR"));
        vect.add(nf.format(gR1b));
        vect.add(nf.format(gR2b));
        vect.add(nf.format(gR3b));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qbd (portata base di progetto)"));
        vect.add(nf.format(Qbk / gR1b));
        vect.add(nf.format(Qbk / gR2b));
        vect.add(nf.format(Qbk / gR3b));
        redditi.addRow(vect);


        //portata di TOTALE
        vect = new Vector<String>();
        vect.add(new String("PORTATA TOTALE"));
        vect.add(new String(""));
        vect.add(new String(""));
        vect.add(new String(""));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qtotd (portata compressione totale lorda)"));
        vect.add(nf.format(Qbk / gR1b + Qlk / gR1));
        vect.add(nf.format(Qbk / gR2b + Qlk / gR2));
        vect.add(nf.format(Qbk / gR3b + Qlk / gR3));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qtotd (portata trazione totale lorda)"));
        vect.add(nf.format(-Qlk / gR1t));
        vect.add(nf.format(-Qlk / gR2t));
        vect.add(nf.format(-Qlk / gR3t));
        redditi.addRow(vect);

        vect = new Vector<String>();
        double pp = del.calcolaPesoPalo(true);
        vect.add(new String("Peso proprio palo (netto sottospinta)"));
        vect.add(nf.format(pp));
        vect.add(nf.format(pp));
        vect.add(nf.format(pp));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qtotd (portata compressione totale netta)"));
        vect.add(nf.format(Qbk / gR1b + Qlk / gR1-pp));
        vect.add(nf.format(Qbk / gR2b + Qlk / gR2-pp));
        vect.add(nf.format(Qbk / gR3b + Qlk / gR3-pp));
        redditi.addRow(vect);

        vect = new Vector<String>();
        vect.add(new String("Qtotd (portata trazione totale netta)"));
        vect.add(nf.format(-Qlk / gR1t-pp));
        vect.add(nf.format(-Qlk / gR2t-pp));
        vect.add(nf.format(-Qlk / gR3t-pp));
        redditi.addRow(vect);




        SpalleTable tIcef = new SpalleTable(redditi, cellFont, headers);
        tIcef.setFontHeader(cellHeaderFont);


        int[] al = {Element.ALIGN_LEFT, Element.ALIGN_CENTER,
            Element.ALIGN_CENTER, Element.ALIGN_CENTER
        };
        tIcef.setCellAllignment(al);


        int[] data_type = {PdfUtil.TEXT, PdfUtil.TEXT, PdfUtil.TEXT,
            PdfUtil.TEXT
        };
        tIcef.setColumnDataType(data_type);

        Table datatable = tIcef.createTable();
        document.add(datatable);


    }
}
