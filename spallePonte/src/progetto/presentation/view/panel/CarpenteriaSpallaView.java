package progetto.presentation.view.panel;

/*
 * Created on 23-mar-2004
 * Andrea Cavalieri
 *
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

import progetto.presentation.view.grafica.Drawing;
import javax.swing.JPanel;

import progetto.model.bean.Appoggio;
import progetto.model.bean.Carico;
import progetto.model.bean.CaricoForze;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;

/**
 * @author Andrea
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CarpenteriaSpallaView
        extends JPanel {

//trasformazione originale
    AffineTransform atOrigine;
    //variabili globali del progetto
    private int Bxf, Byf; //fonda
    private double alfa;

    //pila
    private int dxG, dyG; //disassamento pila
    private int Bxp, Byp; //dimensioni

    //muri d'ala
    private int Bxm, Bym;

    //paraghiaia
    private int SpParag;
    private int dyPara;

    //variabili di utilita grafica
    private int Bfinestra, Hfinestra;

    private Color coloreCarpenteria = Color.BLACK;
    private Color coloreAssi = Color.ORANGE;
    private Color coloreQuote = Color.RED;

    private int ddquote;

    private int x, lx, y, ly;

    private double[][] xAppoggi;

    private int nAppoggi;

    private boolean NoMuroAlaDx;
    private boolean NoMuroAlaSx;

    private static CarpenteriaSpallaView impView;

    /**
     *
     */
    private CarpenteriaSpallaView() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CarpenteriaSpallaView getInstance() {
        if (impView == null) {
            impView = new CarpenteriaSpallaView();
        }
        return impView;
    }

    /**
     * The paint method provides the real magic. Here we cast the Graphics
     * object to Graphics2D to illustrate that we may use the same old graphics
     * capabilities with Graphics2D that we are used to using with Graphics.
     *
     */
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        try {

            Graphics2D g2d = (Graphics2D) g;

            //salva la trasformazione originaria
            atOrigine = g2d.getTransform();

            //salva le variabili globali in variabili locali
            getVariabiliLocali();
            disegnaPianta(g2d);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //disegna la pianta a livello fondazioni nel riquadro 1 del pannello
    void disegnaPianta(Graphics2D g) {
        //variabili
        AffineTransform at = new AffineTransform();

        java.awt.geom.GeneralPath dd = new java.awt.geom.GeneralPath();

        double tga = -Math.tan(alfa / 180 * 3.14);
        int dy;

        //imposta gli assi
        setGraficaPannello(g);
        //disegna la pianta ciabatta
        x = -Bxf / 2;
        lx = Bxf;
        y = -Byf / 2 + (int) (tga * x);
        ly = Byf;
        dy = (int) (tga * lx);
        dd.moveTo(x, y);
        dd.lineTo(x + lx, y + dy);
        dd.lineTo(x + lx, y + dy + ly);
        dd.lineTo(x, y + ly);
        dd.lineTo(x, y);
        g.setColor(Color.LIGHT_GRAY);
        g.fill(dd);
        g.setColor(coloreCarpenteria);
        g.draw(dd);

        //disegnPila
        x = -Bxp / 2;
        lx = Bxp;
        y = -Byp / 2 + (int) (tga * x);
        ly = Byp;
        dy = (int) (tga * lx);

        at.setToTranslation(dxG, -dyG);
        g.transform(at);
        dd.reset();
        dd.moveTo(x, y);
        dd.lineTo(x + lx, y + dy);
        dd.lineTo(x + lx, y + dy + ly);
        dd.lineTo(x, y + ly);
        dd.lineTo(x, y);
        g.setColor(Color.gray);
        g.fill(dd);
        g.setColor(coloreCarpenteria);
        g.draw(dd);

        //muri d'ala
        x = -Bxp / 2;
        y = Byp / 2 + (int) (tga * x);
        lx = Bxm;
        ly = Bym;
        dy = (int) (tga * lx);
        if (!NoMuroAlaSx) {
            dd.reset();
            dd.moveTo(x, y);
            dd.lineTo(x + lx, y + dy);
            dd.lineTo(x + lx, y + dy + ly);
            dd.lineTo(x, y + ly);
            dd.lineTo(x, y);
            g.setColor(Color.gray);
            g.fill(dd);
            g.setColor(coloreCarpenteria);
            g.draw(dd);
        }

            x = Bxp / 2 - Bxm;
            y = Byp / 2 + (int) (tga * x);
        if (!NoMuroAlaDx) {
            dd.reset();
            dd.moveTo(x, y);
            dd.lineTo(x + lx, y + dy);
            dd.lineTo(x + lx, y + dy + ly);
            dd.lineTo(x, y + ly);
            dd.lineTo(x, y);
            g.setColor(Color.gray);
            g.fill(dd);
            g.setColor(coloreCarpenteria);
            g.draw(dd);
        }

        //paraghiaia
        x = -Bxp / 2;
        y = Byp / 2 - SpParag + (int) (tga * x) - dyPara;
        lx = Bxp;
        ly = SpParag;
        dy = (int) (tga * lx);
        dd.reset();
        dd.moveTo(x, y);
        dd.lineTo(x + lx, y + dy);
        dd.lineTo(x + lx, y + dy + ly);
        dd.lineTo(x, y + ly);
        dd.lineTo(x, y);
        g.setColor(Color.DARK_GRAY);
        g.fill(dd);
        g.setColor(coloreCarpenteria);
        g.draw(dd);

        //appoggi
        for (int i = 0; i < nAppoggi; ++i) {
            setGraficaPannello(g);
            at.setToTranslation(xAppoggi[0][i] + dxG, -xAppoggi[1][i] - dyG);
            g.transform(at);
            dd.reset();
            int lapp = (int) ((Byp - SpParag) * 0.8);
            x = -lapp / 2;
            lx = lapp;
            y = -lapp / 2 + (int) (tga * x);
            ly = lx;
            dy = (int) (tga * lx);
            dd.moveTo(x, y);
            dd.lineTo(x + lx, y + dy);
            dd.lineTo(x + lx, y + dy + ly);
            dd.lineTo(x, y + ly);
            dd.lineTo(x, y);
            g.setColor(Color.BLACK);
            g.fill(dd);
            g.draw(dd);

        }

        //assi
        setGraficaPannello(g);
        g.setColor(coloreAssi);
        Drawing.disegnaFreccia(g, 0, 0, 300,
                0, ddquote, 0, "X");
        Drawing.disegnaFreccia(g, 0, 0, 0,
                300, ddquote, 0, "Y");

//quota
        quota(g);

    }

    private void quota(Graphics2D g) {
        //variabili
        int dx1 = (Bxf - Bxp) / 2 + dxG;
        int dx2 = (Bxf - Bxp) / 2 - dxG;
        int dy1 = (Byf - Byp) / 2 + dyG;

        //imposta gli assi
        AffineTransform at = new AffineTransform();
        setGraficaPannello(g);
        g.setColor(coloreQuote);

        double tga = -Math.tan(alfa / 180 * Math.PI);

        at.setToTranslation(0, +(int) (tga * Bxf / 2));
        g.transform(at);

        //quote x
        Drawing.disegnaQuotaTraDuePunti(-Bxf / 2, -Byf / 2, Bxf / 2, -Byf / 2,
                -ddquote * 6, "", ddquote, g);
        Drawing.disegnaQuotaTraDuePunti(-Bxf / 2, -Byf / 2, -Bxf / 2 + dx1,
                -Byf / 2,
                -ddquote * 3, "", ddquote, g);

        Drawing.disegnaQuotaTraDuePunti(-Bxf / 2 + dx1, -Byf / 2,
                -Bxf / 2 + dx1 + Bxp,
                -Byf / 2, -ddquote * 3, "", ddquote, g);

        if (Bxm != 0) {
            Drawing.disegnaQuotaTraDuePunti(Bxf / 2 - dx2, -Byf / 2, Bxf / 2,
                    -Byf / 2,
                    -ddquote * 3, "", ddquote, g);
        }

        //quote y
        Drawing.disegnaQuotaTraDuePunti(Bxf / 2, Byf / 2, Bxf / 2, -Byf / 2,
                ddquote * 6,
                "", ddquote, g);
        Drawing.disegnaQuotaTraDuePunti(Bxf / 2, Byf / 2, Bxf / 2, Byf / 2 - dy1,
                ddquote * 3, "", ddquote, g);
        Drawing.disegnaQuotaTraDuePunti(Bxf / 2, Byf / 2 - dy1, Bxf / 2,
                Byf / 2 - dy1 - Byp, ddquote * 3, "",
                ddquote, g);
        Drawing.disegnaQuotaTraDuePunti(Bxf / 2, Byf / 2 - dy1 - Byp, Bxf / 2,
                -Byf / 2,
                ddquote * 3, "", ddquote, g);

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

        fx = Math.min((W) / (Bxf * 1.5), (H) / ((Byf + Math.tan(alfa / 180 * 3.14) * Bxf) * 1.5));
        fy = fx;

        xOrig = W / 2;
        Yorig = H / 2;

        //imposta grafica
        at.setToTranslation(xOrig, Yorig);
        g.transform(at);
        at.setToScale(fx, fy);
        g.transform(at);

        //variabili di utilit grafica
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ddquote = (int) (screenSize.width / 240 / fy);

    }

    //salva le variabili del progetto in variabili private della
    //classe presente
    void getVariabiliLocali() {
        SpallaManager man = SpallaManager.getInstance();
        Spalla spalla = man.getCurrentSpalla();

        //double gammaTerreno = spalla.getGammaTerreno();
        //fonda
        int prop = 100;

        Bxf = Math.round((float) (spalla.getBxFonda() * prop));
        Byf = Math.round((float) (spalla.getByFonda() * prop));

        //pila
        dxG = Math.round((float) (spalla.getXgElevazione() * prop));
        dyG = Math.round((float) (spalla.getYgElevazione() * prop));
        Bxp = Math.round((float) (spalla.getBxElevazione() * prop));
        Byp = Math.round((float) (spalla.getByElevazione() * prop));

        //muri d'ala
        Bxm = Math.round((float) (spalla.getSpMuri() * prop));
        Bym = Math.round((float) (spalla.getLMuri() * prop));

        //paraghiaia
        SpParag = Math.round((float) (spalla.getSpPara() * prop));
        dyPara = Math.round((float) (spalla.getDyPara() * prop));

        NoMuroAlaDx = spalla.isNoMuroAlaDx();
        NoMuroAlaSx = spalla.isNoMuroAlaSx();

        //angolo impalcato
        alfa = spalla.getAlfa();

        //appoggi
        ArrayList appoggi = spalla.getAppoggi();
        nAppoggi = appoggi.size();
        Appoggio appoggio;
        xAppoggi = new double[3][nAppoggi];
        for (int k = 0; k < nAppoggi; k++) {
            appoggio = (Appoggio) appoggi.get(k);
            xAppoggi[0][k] = appoggio.getX() * prop;
            xAppoggi[1][k] = appoggio.getY() * prop;
            xAppoggi[2][k] = appoggio.getZ() * prop;
        }

    }

    void setOriginalTrasformation(Graphics2D g) {
        g.setTransform(atOrigine);
    }

    private void jbInit() throws Exception {
        /*this.setMinimumSize(new Dimension(1000, 1000));
         this.setVerifyInputWhenFocusTarget(true);
         this.setPreferredSize(new Dimension(2000, 5000));*/
    }
}
