package progetto.presentation.view.grafica;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Drawing {
  public  Drawing() {
  }

  //disegna una quota tra due punti x1 e x2
    //se non si mette la stringa della quota calcola la differenza
    //tra x1 e x2
    public static void disegnaQuotaTraDuePunti(int x1, int x2, String quota, int dd,
                                 Graphics2D g2d) {

      int dx = Math.abs(x2 - x1);
      if (quota == "") {
        quota = Integer.toString(dx);
      }

      Line2D ln = new Line2D.Double();
      //linea principale
      ln.setLine(x1 - dd, 0, x2 + dd, 0);
      g2d.draw(ln);
      //linea  di estensione
      ln.setLine(x1 + dd, dd, x1 - dd, -dd);
      g2d.draw(ln);
      ln.setLine(x2 + dd, dd, x2 - dd, -dd);
      g2d.draw(ln);
      ln.setLine(x1, dd, x1, -dd);
      g2d.draw(ln);
      ln.setLine(x2, dd, x2, -dd);
      g2d.draw(ln);
     Font fn = new Font("Arial", 0, (int) (dd * 1.8));
     g2d.setFont(fn);

      FontMetrics metrics = g2d.getFontMetrics();
      int tW = metrics.stringWidth(quota);
      if (dx > tW) {
        g2d.drawString(quota, x2 / 2 + x1 / 2 - tW / 2, -dd / 2);
      }
      else {
        g2d.drawString(quota, Math.max(x2, x1) + dd, 0);
      }

  }

  //disegna una quota tra due punti x1,y1 e x2,y2
   //se non si mette la stringa della quota calcola la differenza
   //tra x1 e x2
   public static void disegnaQuotaTraDuePunti(int x1, int y1, int x2, int y2, int Dist,
                                String quota, int dd,
                                Graphics2D g2d) {

     AffineTransform at0 = g2d.getTransform();

     //trasla gli assi in x1,y1
     AffineTransform atn = new AffineTransform();
     atn.setToTranslation(x1, y1);
     g2d.transform(atn);
     //ruota gli assi
     double teta = 0;
     if ( (x1 - x2) != 0) {
       teta = Math.atan( (y1 - y2) / (x2 - x1));
     }
     else {
       teta = -Math.PI / 2;
     }
     atn.setToRotation(teta);
     g2d.transform(atn);
     //trasla ancora gli assi di Dist
     atn.setToTranslation(0, Dist);
     g2d.transform(atn);

     int l = (int) (Math.pow(Math.pow( (x2 - x1), 2) + Math.pow( (y2 - y1), 2),
                             0.5));
     disegnaQuotaTraDuePunti(0, l, quota, dd, g2d);

     g2d.setTransform(at0);
   }

    //disegna una freccia
   public static void disegnaFreccia(Graphics2D g, int x1, int y1,
                               int x2, int y2, int dd, int stile, String testo) {
     Line2D ln = new Line2D.Double();
     int[] x = new int[4];
     int[] y = new int[4];

//trasforma gli assi
     AffineTransform at0 = g.getTransform();

     //trasla gli assi in x1,y1
     AffineTransform atn = new AffineTransform();
     atn.setToTranslation(x1, y1);
     g.transform(atn);
 
     //ruota gli assi
     double teta = 0;
     double x1d=x1;
     double y1d=y1;
     double x2d=x2;
     double y2d=y2;
     if ( (x1 - x2) != 0) {
		 teta = Math.atan((y1d - y2d) / Math.abs(x2d - x1d));
    	 
    	 /*    	 if ( x2 > x1 ) {
    		 teta = Math.atan((y1d - y2d) / (x2d - x1d));
    		 }
    	 else{
    		 teta =3.14 - Math.atan((y1d - y2d) / (x2d - x1d));
    	 }
  */
     }
     else {
       teta = -3.14 / 2;
     }
 
     atn.setToRotation(teta);
     g.transform(atn);

     //linea principale
     int l = (int) (Math.pow(Math.pow( (x2 - x1), 2) + Math.pow( (y2 - y1), 2),
                             0.5));
     ln.setLine(0, 0, l, 0);
     g.draw(ln);

     //freccia
     x[0] = l;
     y[0] = 0;
     x[1] = l - 2 * dd;
     y[1] = dd;
     x[2] = l - dd;
     y[2] = 0;
     x[3] = l - 2 * dd;
     y[3] = -dd;
     Polygon pol = new Polygon(x, y, 4);
     g.fill(pol);
     g.draw(pol);

     //testo
     Font fn = new Font("Arial", 0, (int) (dd * 1.8));
     g.setFont(fn);
     
     g.drawString(testo, l, -dd);
     //reimposta grafica
     g.setTransform(at0);

  }

//disegna un "leader" e relativo testo
  public static void leader(int x, int y, int dx,int dy,int dd, String testo,
                            Graphics2D g2d){

    int x2=x+dx;
    int y2=y+dy;
    int x3=x2+dd;
    int y3=y2;


    //linee
    Line2D ln = new Line2D.Double();
   //linea principale
    ln.setLine(x, y, x2 ,y2);
    g2d.draw(ln);
    ln.setLine(x2, y2, x3 ,y3);
    g2d.draw(ln);

    FontMetrics metrics = g2d.getFontMetrics();
    int tW = metrics.stringWidth(testo);
    int tH = (metrics.getMaxAscent()-metrics.getMaxDescent());

    if(dd>=0) g2d.drawString(testo,x3+dd/30,y3+tH/2);
    else g2d.drawString(testo,x3-tW,y3+tH/2);

  }

}
