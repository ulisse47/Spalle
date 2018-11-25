package progetto.model.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FondazioneSuperficiale
    extends SistemaDiFunzioni {
  double Bx, By;
  double[] w0;

  public FondazioneSuperficiale(double Bx, double By, double [] w0) {
    this.Bx = Bx;
    this.By = By;
    this.w0=w0;
  }


  public double getFi(double[] x, int n) throws java.lang.Exception{

    double[] F = getValori(x);
    return F[n];

  }


  public double getNorma(double[] x) throws java.lang.Exception {

    double n=0;
    double[] F = getValori(x);

    for(int i=0;i<x.length;i++){
      n+= F[i]*F[i];
    }

    return Math.pow(n,.5);

 //   throw new java.lang.UnsupportedOperationException(
 //       "Method getNorma() not yet implemented.");

  }



  public double[] getValori(double[] x) throws java.lang.Exception {

    double w=x[0];
    double v=x[1];

    double A = w*v/6;
    double B = v*Math.pow((w-Bx),3)/(6*w*w);
    double C = w*Math.pow((v-By),3)/(6*v*v);
    double[] F=new double [2];

    if (w<=Bx) B=0;
    if (v<=By) C=0;

    double V =getV(x);

    double Sn = A*w/4-B*(Bx+(w-Bx)/4)-C*(w*(v-By)/(4*v));
    double Sz = A*v/4-B*(v*(w-Bx)/(4*w))-C*(By+(v-By)/4);

    F[0]=Sn/V-w0[0];
    F[1]=Sz/V-w0[1];

  //  throw new java.lang.UnsupportedOperationException(
   //     "Method getValori() not yet implemented.");

    return F;

  }

  public double getV(double[] x){

  double w=x[0];
  double v=x[1];

  double A = w*v/6;
  double B = v*Math.pow((w-Bx),3)/(6*w*w);
  double C = w*Math.pow((v-By),3)/(6*v*v);

  if (w<=Bx) B=0;
  if (v<=By) C=0;

  return A-B-C;

  }

}
