/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.model.bean;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Terreno {

    private double h;//altezza starto
    private double gamma;//gamma terreno

    
    private double fi;//angolo attrito interno
    private double c;//coesione
    private double ka;//coesione spinta
    private double alfa;//

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }
    /**
     * 
     */
    public Terreno(double h,double fi, double gamma
            , double cu, double k, double alfa) {
        this.h = h;
        this.gamma = gamma;
 
        
        this.fi = fi;
        this.c = cu;
        this.ka = k;
        this.alfa = alfa;
        
    }

    /**
     * 
     */
    public Terreno() {
        h= 30;
        gamma = 20;
        fi= 30;
        c = 0.0;
        ka=0.7;
        alfa = 0;
        
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getFi() {
        return fi;
    }

    public void setFi(double fi) {
        this.fi = fi;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }



    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getKa() {
        return ka;
    }

    public void setKa(double ka) {
        this.ka = ka;
    }
}
