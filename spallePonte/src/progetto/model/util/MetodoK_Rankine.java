/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.model.util;

/**
 *
 * @author Marco
 */
public class  MetodoK_Rankine implements MetodiCalcoloSpintaTerreno{

    public double getK(double fi) {
     return ((1-Math.sin(fi)) / (1+Math.sin(fi)) );   
    }

     @Override
    public String toString() {
        return "Ka - Rankine";
    }

    public double getK(double fi, double delta) {
        return getK(fi);
    }
    
}
