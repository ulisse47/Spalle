package progetto.model.util;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Matematica {
  public Matematica() {
  }

  private double[] getFunzione(double[] x) {
    double[] f = {
        0, 1};

    return f;
  }

  public static double[] ZeriFunzioni_MetodoNewton(SistemaDiFunzioni F,
        double[] x, int Dimensione, int maxIter, double err) throws Exception {

      int nIter = 0;
      double[] x2 = new double[Dimensione];

      try {
        do {
          ++nIter;
          for (int i = 0; i < Dimensione; i++) {
            x2[i] = x[i] - F.getFi(x,i)/getDerivata(F,x,0.01,i);
            x[i] = x2[i];
          }

        } //mettere gestione degli errori
        while (nIter < maxIter & F.getNorma(x2) > err);
        System.out.println("Norma = " + F.getNorma(x2));
        System.out.println("iterazioni = " + nIter);
        System.out.println("wx = " + x2[0]);
        System.out.println("wy = " + x2[1]);

        return x2;
      }
      catch (Exception ex) {

      }

      return x;
  }

  public static double[] ZeriFunzioni_MetodoSecante(SistemaDiFunzioni F,
      double[] xa,
      double xb[], int Dimensione, int maxIter,
      double err) throws Exception {
    double[] fa;
    double[] fb;
    double[] f0 = new double[Dimensione];
    double[] x0 = new double[Dimensione];
    int nIter = 0;
    try {
      do {
        ++nIter;
        fa = F.getValori(xa);
        fb = F.getValori(xb);

        for (int i = 0; i < Dimensione; i++) {
          x0[i] = xa[i] - fa[i] * (fa[i] - fb[i]) / (xa[i] - xb[i]);
          xb[i] = xa[i];
          xa[i] = x0[i];
        }

      } //mettere gestione degli errori
      while (nIter < maxIter & F.getNorma(x0) > err);
      System.out.println(F.getNorma(x0));
      System.out.println(x0[0]);
      System.out.println(x0[1]);

      return x0;
    }
    catch (Exception ex) {

    }

    return f0;
  }

  //calcola le derivate del sistema di equazioni
  private static double getDerivata(SistemaDiFunzioni F, double x[],double dx, int n) {
    int l = x.length;
    double m=0;
    double[] xl =  new double[x.length];

    for(int i =0; i< x.length;++i) xl[i]=x[i];

    try {
      double fa = F.getFi(x, n);
      xl[n] += xl[n] * dx;
      double fb = F.getFi(xl, n);
      m=(fa-fb)/(x[n]-xl[n]);
    }
    catch (Exception ex) {

    }

    return m;
  }
}
