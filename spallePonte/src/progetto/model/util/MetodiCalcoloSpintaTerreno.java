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
public interface MetodiCalcoloSpintaTerreno {
    
    public double getK (double fi);

    public double getK (double fi,double delta);
    
    @Override
    public String toString();
    
}
