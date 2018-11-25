/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.model.util;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

/**
 *
 * @author Marco
 */
public class Metodo_MononobeOkabe implements MetodiCalcoloSpintaTerreno {

    double beta_m = 1;
    double sAg = 0.2;
    double kh;
    double kv;
    double teta;

    public double getK(double fi) {

        return getK(fi, 0);
    }

    public double getBeta_m() {
        return beta_m;
    }

    public void setBeta_m(double beta_m) {
        this.beta_m = beta_m;
    }

    @Override
    public String toString() {
        return "Kd+s - Mononobe Okabe";
    }

    public double getsAg() {
        return sAg;
    }

    public void setsAg(double sAg) {
        this.sAg = sAg;
    }

    public double getK(double fi, double delta) {
        double beta = 0;
        double i = 0;
        kh = beta_m * sAg;
        kv = 0.5 * kh;
        teta = Math.atan(kv / (1 + kv));
        double c1 = Math.cos(fi -beta- teta) * Math.cos(fi -beta- teta);
        double c2 = Math.cos(beta)*Math.cos(beta) * Math.cos(teta) * Math.cos(delta+beta+teta);
        double c3 = Math.sin(fi + delta) * Math.sin(fi - i - teta);
        double c4 = Math.cos(delta + beta + teta) * Math.cos(i - beta);
        double k = c1 / (c2 * Math.pow(1 + Math.pow(c3 / c4, 0.5), 2));
        return k;
    }

}
