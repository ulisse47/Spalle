package progetto.presentation.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import progetto.model.bean.Palo;
import progetto.model.bean.Spalla;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
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
public class FondazioniView
    extends JPanel {

  //trasformazione originale
  AffineTransform atOrigine;
  //variabili globali del progetto
  private int Bxf, Byf; //fonda

  //pila
  private int dxG, dyG; //disassamento pila
  private int Bxp, Byp; //dimensioni

  //variabili di utilità grafica
  private int Bfinestra, Hfinestra;

  //pressioni terreno
  int w, v;
  double Mx, My;

  //pali
  private int nPali;
  private double[][] pali;
  
  private double alfa;

  private Color coloreCarpenteria = Color.BLACK;
  
  int ddquote;

  private static FondazioniView impView;


  private FondazioniView() {
    super();
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  void setOriginalTrasformation(Graphics2D g) {
    g.setTransform(atOrigine);
  }

  public static synchronized FondazioniView getInstance() {
    if (impView == null) {
      impView = new FondazioniView();
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
      disegnaPianta(g2d);

      if (nPali > 0) {
        disegnaPali(g2d);
      }

      if (nPali == 0) {
      	disegnaPressioni(g2d);

      }

    }

    catch (Exception e) {
      e.printStackTrace();
    }
  }

//
  void disegnaPressioni(Graphics2D g) {

    //Rectangle2D re = new Rectangle2D.Double();
  	SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
  	  	
  	Polygon p = del.createPoligonoPressioni();
  	
    g.setColor(Color.RED);
    g.fill(p);
    g.draw( p );

  }

  //disegna i pali se nPali>0
  void disegnaPali(Graphics2D g) {

    
   
    Ellipse2D sh = new Ellipse2D.Double();
    double x, y, d;

    g.setColor(coloreCarpenteria);

    setGraficaPannello(g);

  
     g.setColor(coloreCarpenteria);

     for (int i = 0; i < nPali; ++i) {
      x = pali[i][0];
      y = -pali [i][1];
      d = pali[i][2];
      
      sh.setFrame(x-d/2, y-d/2, d, d);
      g.setColor(Color.BLACK);
      g.draw(sh);
      
      Font fn = new Font("Arial", 0, (int) (80 * 1.8));
      g.setFont(fn);

      FontMetrics metrics = g.getFontMetrics();
      String npalo=Integer.toString(i+1);
      int tW = metrics.stringWidth(npalo);
      g.drawString(npalo, (int)(x-tW/2), (int)(y));

   }

  }

  //disegna la pianta a livello fondazioni nel riquadro 1 del pannello
  void disegnaPianta(Graphics2D g) {
    //variabili
    AffineTransform at = new AffineTransform();


    java.awt.geom.GeneralPath dd = new java.awt.geom.GeneralPath ();
    
    int x,lx,y,ly;
    
    double tga=-Math.tan(alfa/180*3.14);
    int dy;
    
    
    //imposta gli assi
    setGraficaPannello(g);
    
    //imposta gli assi
    setGraficaPannello(g);
    //disegna la pianta ciabatta
    x=-Bxf / 2;
    lx=Bxf;
    y=-Byf / 2+(int)(tga*x);
    ly=Byf;
    dy=(int)(tga*lx);
    dd.moveTo(x, y);
    dd.lineTo(x+lx, y+dy);
    dd.lineTo(x+lx, y+dy+ly);
    dd.lineTo(x, y+ly);
    dd.lineTo(x, y);
    g.setColor(Color.LIGHT_GRAY);
    g.fill(dd);
    g.setColor(coloreCarpenteria);
    g.draw(dd);


  
    //disegnPila
    x=-Bxp / 2;
    lx=Bxp;
    y=-Byp / 2+(int)(tga*x);
    ly=Byp;
    dy=(int)(tga*lx);
    
    at.setToTranslation(dxG, -dyG);
    g.transform(at);
    dd.reset();
    dd.moveTo(x, y);
    dd.lineTo(x+lx, y+dy);
    dd.lineTo(x+lx, y+dy+ly);
    dd.lineTo(x, y+ly);
    dd.lineTo(x, y);
    g.setColor(Color.gray);
    g.fill(dd);
    g.setColor(coloreCarpenteria);
    g.draw(dd);
    

    //disegna freccie momenti
    g.setColor(Color.YELLOW);
    x=+Bxp/2+150;
    y=(int)(-Byp/2+x*tga);
    int lx1=+150;
    int ly1=-(int)(lx1*tga);
    int lx2=200;
    int ly2=-(int)(lx2*tga);
    
       
    Drawing.disegnaFreccia(g,x,y,x+lx1,y+ly1,30,1,"MxxValle");
    Drawing.disegnaFreccia(g,x,y,x+lx2,y+ly2,30,1,"");

    x=Bxp/2+150;
    lx1=150;
    ly1=-(int)(lx1*tga);
    lx2=200;
    ly2=-(int)(lx2*tga);

    y=(int)(+Byp/2+x*tga);
    Drawing.disegnaFreccia(g,x,y,x+lx1,y+ly1,30,1,"MxxMonte");
    Drawing.disegnaFreccia(g,x,y,x+lx2,y+ly2,30,1,"");
    
    
  }
  

  //salva le variabili del progetto in variabili private della
  //classe presente
  public void getVariabiliLocali() {
  	SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
  	
  	//SpallaManager man = SpallaManager.getInstance();
    Spalla spalla = del.loadSpallaCorrenteFromModel();
    
    int prop=100;
    //double gammaTerreno = spalla.getGammaTerreno();
    //fonda
    Bxf = (int) (spalla.getBxFonda()*prop);
    Byf = (int) (spalla.getByFonda()*prop );

    //pila
    dxG = (int) (spalla.getXgElevazione()*prop);
    dyG = (int) (spalla.getYgElevazione()*prop);
    Bxp = (int) (spalla.getBxElevazione()*prop);
    Byp = (int) (spalla.getByElevazione()*prop);
    
    alfa=spalla.getAlfa();
    
    //pali
    Object[] o_pali = spalla.getPalificata().toArray();
    nPali = o_pali.length;
    pali = new double[ nPali ][ 3 ];
    for ( int i = 0; i < nPali ; i++ ){
    	Palo palo = ( Palo )o_pali[ i ];
    	pali[i][0] = palo.getX()*prop;
    	pali[i][1] = palo.getY()*prop;
    	pali[i][2] = palo.getDiametro()*prop;
    }
    
    //Calcoli calcoli = new Calcoli();
    double[] x = del.get_sigma_wx_wy();
    w=(int)  x[1];
    v=(int) x[2];
    double[] mv = del.getM1Combo();
    Mx=mv[3];
    My=mv[4];
    
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
    
    fx=Math.min((W)/(Bxf*1.05), (H)/((Byf+Math.tan(alfa/180*3.14)*Bxf)*1.05));
    fy=fx;

    xOrig = W / 2;
    Yorig = H / 2;

 /*   if (Bxf / W > Byf / H) {
      Bfinestra = (int) (Bxf * 1.4);
      Hfinestra = (int) (Byf * H / W);
      fx = W / (Bfinestra);
      fy = fx;
    }
    else {
      Bfinestra = (int) (Bxf * W / H);
      Hfinestra = (int) (Byf * 1.4);
      fy = H / (Hfinestra);
      fx = fy;
    }

    xOrig = W / 2;
    Yorig = H / 2;
*/
    //imposta grafica

    at.setToTranslation(xOrig, Yorig);
    g.transform(at);
    at.setToScale(fx, fy);
    g.transform(at);

    //variabili di utilità grafica
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ddquote = (int) (screenSize.width / 150 / fy);

  }

  private void jbInit() throws Exception {
  	/*this.setMinimumSize(new Dimension(1000, 1000));
         this.setVerifyInputWhenFocusTarget(true);
         this.setPreferredSize(new Dimension(2000, 5000));*/
  }

}
