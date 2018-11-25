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
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.presentation.view.grafica.Drawing;

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
public class SezioneSpallaView
    extends JPanel {

  //Trasformazione originale
  AffineTransform atOrigine = new AffineTransform();

  //fonda
  int Byf, Spf;

  //pila
  int dyG, Byp;
  int Hp;

  //terreno
  int Ht,Htm; //terreno a valle e a monte

  //paraghiaia
  int SpParag, Hparag ,dyPara;
;

  //utilit grafica
  int Bfinestra, Hfinestra;
  int Htot;
  int ddquote;

  //colori e grafica
  Color ColoreCarpenteria = Color.BLACK;
  Color ColoreFillCarpenteria = Color.gray;
  Color ColoreTerreno = Color.BLACK;
  Color ColoreFillTerreno = Color.orange;
  Color ColoreQuote = Color.RED;
  Color ColoreAssi = Color.DARK_GRAY;


  private static SezioneSpallaView impView;

  private SezioneSpallaView() {
    super();
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static SezioneSpallaView getInstance() {
    if (impView == null) {
      impView = new SezioneSpallaView();
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

      //salva le variabili globali in variabili locali
      getVariabiliLocali();
      disegnaSezione(g2d);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

//disegna la sezione della pila
  void disegnaSezione(Graphics2D g) {

    setGraficaPannello(g);

//carpenteria cls
    int[] x = new int[11];
    int[] y = new int[11];
    x[0] = y[0] = 0;
    x[1] = 0;
    y[1] = Spf;
    x[2] = Byf;
    y[2] = Spf;
    x[3] = Byf;
    y[3] = 0;
    x[4] = Byf / 2 + Byp / 2 - dyG;
    y[4] = 0;
    x[5] = Byf / 2 + Byp / 2 - dyG;
    y[5] = -Hp;
    x[6] = Byf / 2 - Byp / 2 - dyG;
    y[6] = -Hp;
    x[7] = Byf / 2 - Byp / 2 - dyG;
    y[7] = 0;
    Polygon pol = new Polygon(x, y, 8);
    g.setColor(ColoreFillCarpenteria);
    g.fill(pol);
    g.setColor(ColoreCarpenteria);
    g.draw(pol);
    
    //paraghiaia
    Rectangle2D rec = new Rectangle2D.Double();
    rec.setFrame(Byf / 2 + Byp / 2 - dyG - SpParag-dyPara,-Hp-Hparag, SpParag, Hparag);
    g.setColor(ColoreFillCarpenteria);
    g.fill(rec);
    g.setColor(ColoreCarpenteria);
    g.draw(rec);

    
    //disegna terreno
    int[] xt = new int[16];
    int[] yt = new int[16];
    xt[0] = x[3];
    yt[0] = y[3];
    
    xt[1] = x[4];
    yt[1] = y[4];
    
    xt[2] = x[5];
    yt[2] = -Math.min(Hp,Ht);
    
    xt[3] = x[5]-dyPara;
    yt[3] = yt[2];
    
    xt[4] = xt[3];
    yt[4] = -Ht;
    
    xt[5] = 2*Bfinestra;
    yt[5] = yt[4];
    
    xt[6] = 2*Bfinestra;
    yt[6] = Hfinestra;
    
    xt[7] = -2*Bfinestra;
    yt[7] = Hfinestra;
    
    xt[8] = -2*Bfinestra;
    yt[8] = -Htm;
    xt[9] = x[7];
    yt[9] = -Htm;
    
    xt[10] = x[7];
    yt[10] = y[7];
    
    xt[11]=x[0];yt[11]=y[0];
    
    xt[12]=x[1];yt[12]=y[1];
    
    xt[13]=x[2];yt[13]=y[2];
    
    xt[14]=x[3];yt[14]=y[3];
//    xt[15]=x[2];yt[15]=y[2];

    Polygon polT = new Polygon(xt, yt, 15);
    g.setColor(ColoreFillTerreno);
    g.fill(polT);
    g.setColor(ColoreTerreno);
    g.draw(polT);

    //quote X
    g.setColor(ColoreQuote);
    //spalla-plate
    Drawing.disegnaQuotaTraDuePunti(x[1],y[1],x[7],y[1],4*ddquote,"",ddquote,g);
    Drawing.disegnaQuotaTraDuePunti(x[7],y[1],x[4],y[1],4*ddquote,"",ddquote,g);
    Drawing.disegnaQuotaTraDuePunti(x[4],y[1],x[2],y[1],4*ddquote,"",ddquote,g);
    //platea
    Drawing.disegnaQuotaTraDuePunti(x[1],y[1],x[2],y[1],7*ddquote,"",ddquote,g);

    //quote y
    Drawing.disegnaQuotaTraDuePunti(x[2],y[2],x[2],y[3],4*ddquote,"",ddquote,g);
    Drawing.disegnaQuotaTraDuePunti(x[2],y[3],x[2],y[5],4*ddquote,"",ddquote,g);

    if(Hparag!=0)
    	   Drawing.disegnaQuotaTraDuePunti(x[2],y[5],x[2],y[5]+Hparag,4*ddquote,"",ddquote,g);

    Drawing.disegnaQuotaTraDuePunti(x[2],y[2],x[2],y[3],7*ddquote,"",ddquote,g);
    Drawing.disegnaQuotaTraDuePunti(x[2],y[3],x[2],y[5]-Hparag,7*ddquote,"",ddquote,g);
    Drawing.disegnaQuotaTraDuePunti(x[2],y[2],x[2],y[5]-Hparag,10*ddquote,"",ddquote,g);
    
    //quota terreno
    int xm = x[4]/2+x[3]/2;
    Drawing.disegnaQuotaTraDuePunti(xm,y[3],xm,y[3]-Ht,7*ddquote,"",ddquote,g);
    
    
    //assi
    g.setColor(ColoreAssi);
//    Drawing.disegnaFreccia(g,Byf/2,0,Byf/2-300,0,ddquote,0,"Y");
 //   Drawing.disegnaFreccia(g,Byf/2,0,Byf/2,-150,ddquote,0,"Z");

  }

//Imposta posizione e fattore di scala del pannello. Lo centra nello spigolo
//sinistro estradosso fondazione
  void setGraficaPannello(Graphics2D g) {

    double fx, fy, W = 0, H = 0;
    double xOrig = 0, Yorig = 0;

    setOriginalTrasformation(g);

    AffineTransform at = new AffineTransform();

    W = getWidth();
    H = getHeight();

    fx=Math.min((W)/(Byf*1.5), (H)/(Htot*1.5));
    fy=fx;
    
    Bfinestra=(int)(W/fx);
    Hfinestra=(int)(H/fy);
    
 /*  if (Byf / W > Htot / H) {
      Bfinestra = (int) (Byf*1.4);
      Hfinestra = (int) (Htot * H / W);
      fx = W / (Bfinestra);
      fy = fx;
    }
    else {
      Bfinestra = (int) (Byf * W / H);
      Hfinestra = (int) (Htot*1.4);
      fy = H / (Hfinestra);
      fx = fy;
    }
*/
    
    //imposta grafica
    xOrig = W / 2;
    Yorig = H / 2;
    at.setToTranslation(xOrig, Yorig);
    g.transform(at);
    at.setToScale(fx, fy);
    g.transform(at);
    xOrig = -Bfinestra / 2.5;
    Yorig = Htot / 2 - Spf;
    at.setToTranslation(xOrig, Yorig);
    g.transform(at);

    //variabili di utilit grafica
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ddquote = (int) (screenSize.width / 200 / fy);

  }

//salva le variabili del progetto in variabili private della
  //classe presente
  void getVariabiliLocali() {
    SpallaManager man = SpallaManager.getInstance();
    Spalla spalla = man.getCurrentSpalla();

    //double gammaTerreno = spalla.getGammaTerreno();
    //fonda
    int prop=100;
    
    Byf = Math.round((float)(spalla.getByFonda()*prop));
    Spf = Math.round((float)spalla.getSpFonda()*prop);

    //pila
    dyG = Math.round((float)spalla.getYgElevazione()*prop);
    Byp = Math.round((float)spalla.getByElevazione()*prop);
    Hp = Math.round((float)spalla.getHsElevazione()*prop);

    //paraghiaia
    SpParag = Math.round((float)spalla.getSpPara()*prop);
    Hparag = Math.round((float)spalla.getHPara()*prop);
    dyPara =Math.round((float)spalla.getDyPara()*prop);

    //Terreno
    Ht = Math.round((float)spalla.getH_terreno()*prop);
    Htm=Math.round((float)spalla.getH_terrenoValle()*prop);

    //variabili derivabili
    Htot = Spf + Hp + Hparag;

  }

  void setOriginalTrasformation(Graphics2D g) {
    g.setTransform(atOrigine);
  }

  private void jbInit() throws Exception {
    /*this.setMinimumSize(new Dimension(1000, 1000));
    this.setPreferredSize(new Dimension(2000, 5000));
    this.setVerifyInputWhenFocusTarget(true);*/

  }
}
