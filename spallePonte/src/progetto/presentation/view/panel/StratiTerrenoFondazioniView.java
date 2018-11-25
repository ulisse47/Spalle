package progetto.presentation.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;
import javax.swing.JPanel;

import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.Terreno;
import progetto.model.bean.Verticale;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StratiTerrenoFondazioniView
        extends JPanel {

    //trasformazione originale
    AffineTransform atOrigine;
    //variabili globali del progetto
    private int Bxp; //dimensioni

    //variabili di utilità grafica
    private Color coloreCarpenteria = Color.BLACK;
    int ddquote;
    private int dpalo;
    private int Lpalo;
    private int[] Zterreni;
    private String[] ZterreniStr;
    private double[] FIterreni;
    private double[] Kterreni;
    private double[] Alfaterreni;
    private double[] Cuterreni;
    private double[] Gammaterreni;
    private int ZtestaPalo;
    private int Zfalda;
    private String ZfaldaStr;
    private int Hmax;
    private static StratiTerrenoFondazioniView impView;
    private int nterreni;
    private static Color colori[] = new Color[30];
    private int[] deltaNodiInt;
    private int[] ziNodi;
    int nNodi;
    boolean disegnaSpostamenti;
    double scalaSpostamenti;
    private int Wfin,  Hfin;

    private StratiTerrenoFondazioniView() {
        super();
        colori[0] = Color.ORANGE;
        colori[1] = Color.GREEN;
        colori[2] = Color.PINK;
        colori[3] = Color.YELLOW;
        for (int i = 4; i < 30; ++i) {
            colori[i] = Color.getHSBColor((float) Math.random(),
                    (float) Math.random(), (float) Math.random());
        }


        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void setOriginalTrasformation(Graphics2D g) {
        g.setTransform(atOrigine);
    }

    public static synchronized StratiTerrenoFondazioniView getInstance() {
        if (impView == null) {
            impView = new StratiTerrenoFondazioniView();
        }
        return impView;
    }

    /**
     * The paint method provides the real magic.  Here we
     * cast the Graphics object to Graphics2D to illustrate
     * that we may use the same old graphics capabilities with
     * Graphics2D that we are used to using with Graphics.
     **/
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        try {

            Graphics2D g2d = (Graphics2D) g;

            //salva la trasformazione originaria
            atOrigine = g2d.getTransform();
            disegnaSpostamenti = false;

            Verticale v = SpallaManager.getInstance().getCurrentVerticale();
            if (v == null) {
                g2d.clearRect(0, 0, Wfin, Hfin);
                return;
            }

//            delatNodi = SpallaManager.getInstance().getSPOST_NODI();
            if (SpallaManager.getInstance().getSPOST_NODI() != null) {
                disegnaSpostamenti = true;
            }

            //salva le variabili globali in variabili locali
            getVariabili();

            disegnaPalo(g2d);
     
            if (disegnaSpostamenti) {
            disegnaDeformata(g2d);
        }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //disegna la pianta a livello fondazioni nel riquadro 1 del pannello
    void disegnaPalo(Graphics2D g) {
        //variabili


        java.awt.geom.GeneralPath dd = new java.awt.geom.GeneralPath();

        int x, lx, y, ly;

        //imposta gli assi
        setGraficaPannello(g);

        //disegna terreni
        x = -Bxp * 30 / 2;
        lx = Bxp * 30;
        y = 0;
        ly = Zterreni[0];
        for (int i = 0; i < nterreni; ++i) {
            ly = Zterreni[i] - y;
            dd.reset();
            dd.moveTo(x, y);
            dd.lineTo(x + lx, y);
            dd.lineTo(x + lx, y + ly);
            dd.lineTo(x, y + ly);
            dd.lineTo(x, y);
            g.setColor(colori[i]);
            g.fill(dd);
            g.setColor(Color.black);
            g.draw(dd);

            y += ly;

            String zt = "Z = " + ZterreniStr[i];
            //int Hzt = metrics.stringWidth(zt);
            g.setColor(Color.black);
//            g.drawString(zt, (int) (-3 * Bxp), (int) (y-ddquote));
            g.drawString(zt, (int) (-Wfin / 4), (int) (y - ddquote));

            //caratteristiche terreni
            if (FIterreni[i] != 0) {
                String fi = "θ = " + Double.toString(FIterreni[i]) + ", K = " + Double.toString(Kterreni[i]) + ", γ = " + Double.toString(Gammaterreni[i]);
                g.drawString(fi, (int) (Bxp * 1.1), (int) (y - ly / 2));
            } else {
                String fi = "α = " + Double.toString(Alfaterreni[i]) + ", Cu = " + Double.toString(Cuterreni[i]) + ", γ = " + Double.toString(Gammaterreni[i]);
                g.drawString(fi, (int) (Bxp * 1.1), (int) (y - ly / 2));
            }
        }

        //disegna porzione plinto
        x = -Bxp / 2;
        lx = Bxp;
        y = 0;
        ly = ZtestaPalo;
        dd.reset();
        dd.moveTo(x, y);
        dd.lineTo(x + lx, y);
        dd.lineTo(x + lx, y + ly);
        dd.lineTo(x, y + ly);
        dd.lineTo(x, y);
        g.setColor(Color.LIGHT_GRAY);
        g.fill(dd);
        g.setColor(coloreCarpenteria);
        g.draw(dd);



        //disegnPalo
        x = -dpalo / 2;
        lx = dpalo;
        y = ZtestaPalo;
        ly = Lpalo;
        dd.reset();
        dd.moveTo(x, y);
        dd.lineTo(x + lx, y);
        dd.lineTo(x + lx, y + ly);
        dd.lineTo(x, y + ly);
        dd.lineTo(x, y);
        g.setColor(Color.gray);
        g.fill(dd);
        g.setColor(coloreCarpenteria);
        g.draw(dd);

        //falda
        FontMetrics metrics = g.getFontMetrics();
        String zf = "Zfalda = " + ZfaldaStr;
        int wzf = metrics.stringWidth(zf);

        g.setColor(Color.BLUE);
        dd.reset();
//        int centro = -6 * Bxp;
        int centro = -Wfin / 4;
        int lf = wzf / 2;
        dd.moveTo(centro - lf / 2, Zfalda);
        dd.lineTo(centro + lf / 2, Zfalda);
        g.draw(dd);
        dd.moveTo((int) (centro - (lf / 2) * 0.6), Zfalda + ddquote);
        dd.lineTo((int) (centro + (lf / 2) * 0.6), Zfalda + ddquote);
        g.draw(dd);
        dd.moveTo((int) (centro - (lf / 2) * 0.4), Zfalda + 2 * ddquote);
        dd.lineTo((int) (centro + (lf / 2) * 0.4), Zfalda + 2 * ddquote);
        g.draw(dd);

        g.drawString(zf, centro - wzf / 2, Zfalda - (int) (ddquote));

    }


    //salva le variabili del progetto in variabili private della
    //classe presente
    public void getVariabili() {
        SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();

        //SpallaManager man = SpallaManager.getInstance();
        Spalla spalla;
        SpallaManager manager;
        ArrayList st;
        spalla = del.loadSpallaCorrenteFromModel();
        manager = SpallaManager.getInstance();
        st = manager.getCurrentVerticale().getStrati();
        double[][][] def = manager.getSPOST_NODI();

        int n = st.size();

        int prop = 100;
        //double gammaTerreno = spalla.getGammaTerreno();
        //fonda

        Lpalo = (int) (spalla.getLpalo() * prop);
        dpalo = (int) (spalla.getDpalo() * prop);
        ZtestaPalo = (int) (spalla.getZpalo() * prop);
        Zfalda = (int) (spalla.getZfalda() * prop);
        ZfaldaStr = Double.toString(spalla.getZfalda());
        Hmax = (int) (manager.getHmaxTerreni() * prop);

        Bxp = dpalo * 3;

        Zterreni = new int[n];
        ZterreniStr = new String[n];
        Kterreni = new double[n];
        Alfaterreni = new double[n];
        Cuterreni = new double[n];
        FIterreni = new double[n];
        Gammaterreni = new double[n];

        for (int i = 0; i < n; ++i) {
            Terreno t = (Terreno) st.get(i);
            Zterreni[i] = (int) (t.getH() * prop);
            ZterreniStr[i] = Double.toString(t.getH());
            Kterreni[i] = t.getKa();
            FIterreni[i] = t.getFi();
            Alfaterreni[i] = t.getAlfa();
            Cuterreni[i] = t.getC();
            Gammaterreni[i] = t.getGamma();

        }

        if (disegnaSpostamenti) {

            nNodi = def.length;
            double[] deltaNodi = new double[nNodi];
            deltaNodiInt = new int[nNodi];
            ziNodi = new int[nNodi];
            int dh = (int) (Lpalo / (nNodi - 1));
            int zi = ZtestaPalo;
            double max = 0;
            double delta;
            for (int i = 0; i < nNodi; ++i) {
                delta = def[i][1][0];
                deltaNodi[i] = delta;
                ziNodi[i] = zi;
                zi += dh;
                if (Math.abs(delta) > max) {
                    max = Math.abs(delta);
                }
            }
            scalaSpostamenti = (2 * Bxp) / max;
            //scala igli spostamenti
            for (int i = 0; i < nNodi; ++i) {
                deltaNodi[i] *= scalaSpostamenti;
                deltaNodiInt[i] = (int) deltaNodi[i];
            }

        }



//        colori = manager.getColori();

        nterreni = n;
    }

    //Imposta posizione e fattore di scala del pannello. Lo centra nel baricentro
    //della fondazione.
    void setGraficaPannello(Graphics2D g) {

        double fx, fy, W = 0, H = 0;
        double xOrig = 0, Yorig = 0;

        setOriginalTrasformation(g);

        AffineTransform at = new AffineTransform();


        W = getWidth();
        H = getHeight();

        fx = Math.min((W) / (Bxp * 1.1), (H) / (Hmax * 1.1));
        fy = fx;

        Wfin = (int) (W / fx);
        Hfin = (int) (H / fx);

        xOrig = W / 2;
        Yorig = H / 13;


        at.setToTranslation(xOrig, Yorig);
        g.transform(at);
        at.setToScale(fx, fy);
        g.transform(at);

        //variabili di utilità grafica
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ddquote = (int) (screenSize.width / 150 / fy);

        //testo
        Font fn = new Font("Arial", 0, (int) (ddquote * 1.6));
        g.setFont(fn);


    }

    private void disegnaDeformata(Graphics2D g) {
   
        setGraficaPannello(g);
        java.awt.geom.GeneralPath dd = new java.awt.geom.GeneralPath();
        g.setColor(Color.RED);

        //disegna terreni
        dd.reset();
        dd.moveTo(deltaNodiInt[0], ziNodi[0]);
        for (int i = 1; i < nNodi; ++i) {
            dd.lineTo(deltaNodiInt[i], ziNodi[i]);
        }
        g.draw(dd);

    }

    private void jbInit() throws Exception {

    }
}
