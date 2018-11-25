/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.model.bean;

/**
 *
 * @author Marco
 */
public class PalificataWizard_Set {

    private double interasseX;
    private double interasseY;
    private double diametroPali;
    private int nX;
    private int nY;

    public double getDiametroPali() {
        return diametroPali;
    }

    public void setDiametroPali(double diametroPali) {
        this.diametroPali = diametroPali;
    }

    public PalificataWizard_Set() {
        interasseX = 1.2;
        interasseY = 1.2;
        nX = 6;
        nY = 6;
        diametroPali = 1.2;
    }
    
      public PalificataWizard_Set(double ix, double iy, int nx, int ny, double diametro) {
        interasseX = ix;
        interasseY = iy;
        nX = nx;
        nY = ny;
        diametroPali = diametro;
    }

    public void setWizard(double ix, double iy, int nx, int ny, double diametro) {
        interasseX = ix;
        interasseY = iy;
        this.nX = nx;
        this.nY = ny;
        this.diametroPali = diametro;

    }

    public double getInterasseX() {
        return interasseX;
    }

    public void setInterasseX(double interasseX) {
        this.interasseX = interasseX;
    }

    public double getInterasseY() {
        return interasseY;
    }

    public void setInterasseY(double interasseY) {
        this.interasseY = interasseY;
    }

    public int getnX() {
        return nX;
    }

    public void setnX(int nX) {
        this.nX = nX;
    }

    public int getnY() {
        return nY;
    }

    public void setnY(int nY) {
        this.nY = nY;
    }

}
