/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.presentation.util;

import progetto.model.util.MetodiCalcoloSpintaTerreno;

/**
 *
 * @author Marco
 */
public class MetodoKriposo implements MetodiCalcoloSpintaTerreno {

    public double getK(double fi) {
        return 1-Math.sin(fi);
    }

    @Override
    public String toString() {
        return "K0 - spinta a riposo"; //To change body of generated methods, choose Tools | Templates.
    }

    public double getK(double fi, double delta) {
        return getK( fi);
    }
    
    
    
}
