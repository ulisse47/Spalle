/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.model.bean;

import java.util.ArrayList;
import progetto.model.util.MetodiCalcoloSpintaTerreno;
import progetto.model.util.MetodoK_Rankine;
import progetto.model.util.Metodo_MononobeOkabe;
import progetto.presentation.util.MetodoKriposo;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Spalla {

    private String nome = "";
    //CARICHI
    private ArrayList carichi = new ArrayList();
    //APPOGGI
    private ArrayList appoggi = new ArrayList();
    //COMBINAZIONI
    private ArrayList combinazioni = new ArrayList();
    //VERTICALI INDAGATE
    //VERTICALI INDAGATE
    private ArrayList verticaliIndagate = new ArrayList();
    //TERRENO E CALCESTRUZZO
    private boolean conSpintaAttiva = false;
    private double k_static;
    private double k_dinamic;
    
    private double beta_m;

    private boolean k_static_auto = false;
    private boolean k_dinamic_auto = false;

    private MetodiCalcoloSpintaTerreno metodo_Kstatic = new MetodoKriposo();
    private MetodiCalcoloSpintaTerreno metodo_Kdinamic = new Metodo_MononobeOkabe();
    private MetodiCalcoloSpintaTerreno metodo_Kdinamic_coomponent = new MetodoK_Rankine();

    private double fi_Terreno;
    private double k_static_dinamic;
    private double q_carico;
    private double gammaTerreno;
    private double gammaCLS;
    private double h_terreno;
    private double h_terrenoValle;
    private double deltaTerreno;
    private boolean spintaVerticale;
    //FONDAZIONI
    private ArrayList palificata = new ArrayList();
    //lunghezza pali
    private double Lpalo;
    private double Zpalo;
    private double Dpalo;
    private double Zfalda;

    private double bxFonda;
    private double byFonda;
    private double spFonda;
    private double alfa;
    private double x_verifica;
    private double y_verifica;
    //ELEVAZIONI
    private double xgElevazione;
    private double ygElevazione;
    private double bxElevazione;
    private double byElevazione;
    private double hsElevazione;
    //PARAGHIAIA
    private double hPara;
    private double spPara;
    private double dyPara;
    //MURI D'ALA
    private double lMuri;
    private double spMuri;
    private boolean noMuroAlaDx;
    private boolean noMuroAlaSx;
    
        
    //pali
    private double kpali;
    //sisma
    private double agS;

    /**
     *
     */
    public Spalla() {
        super();

    }

    public boolean isNoMuroAlaDx() {
        return noMuroAlaDx;
    }

    public void setNoMuroAlaDx(boolean noMuroAlaDx) {
        this.noMuroAlaDx = noMuroAlaDx;
    }

    public boolean isNoMuroAlaSx() {
        return noMuroAlaSx;
    }

    public void setNoMuroAlaSx(boolean noMuroAlaSx) {
        this.noMuroAlaSx = noMuroAlaSx;
    }

   
    public double getBeta_m() {
        return beta_m;
    }

    public void setBeta_m(double beta_m) {
        this.beta_m = beta_m;
    }

    public boolean isK_dinamic_auto() {
        return k_dinamic_auto;
    }

    public void setK_dinamic_auto(boolean k_dinamic_auto) {
        this.k_dinamic_auto = k_dinamic_auto;
    }

    public MetodiCalcoloSpintaTerreno getMetodo_Kdinamic() {
        return metodo_Kdinamic;
    }

    public void setMetodo_Kdinamic(MetodiCalcoloSpintaTerreno metodo_Kdinamic) {
        this.metodo_Kdinamic = metodo_Kdinamic;
    }

    public MetodiCalcoloSpintaTerreno getMetodo_Kdinamic_coomponent() {
        return metodo_Kdinamic_coomponent;
    }

    public void setMetodo_Kdinamic_coomponent(MetodiCalcoloSpintaTerreno metodo_Kdinamic_coomponent) {
        this.metodo_Kdinamic_coomponent = metodo_Kdinamic_coomponent;
    }

    public ArrayList getAppoggi() {
        return appoggi;
    }

    public double getLpalo() {
        return Lpalo;
    }

    public boolean isK_static_auto() {
        return k_static_auto;
    }

    public MetodiCalcoloSpintaTerreno getMetodo_Kstatic() {
        return metodo_Kstatic;
    }

    public double getFi_Terreno() {
        return fi_Terreno;
    }

    public void setFi_Terreno(double fi_Terreno) {
        this.fi_Terreno = fi_Terreno;
    }

    public void setMetodo_Kstatic(MetodiCalcoloSpintaTerreno metodo_Kstatic) {
        this.metodo_Kstatic = metodo_Kstatic;
    }

    public void setK_static_auto(boolean k_static_auto) {
        this.k_static_auto = k_static_auto;
    }

    public void setLpalo(double Lpalo) {
        this.Lpalo = Lpalo;
    }

    public double getZfalda() {
        return Zfalda;
    }

    public void setZfalda(double Zfalda) {
        this.Zfalda = Zfalda;
    }

    public double getZpalo() {
        return Zpalo;
    }

    public double getDpalo() {
        return Dpalo;
    }

    public void setDpalo(double Dpalo) {
        this.Dpalo = Dpalo;
    }

    public void setZpalo(double Zpalo) {
        this.Zpalo = Zpalo;
    }

    public ArrayList getVerticaliIndagate() {
        return verticaliIndagate;
    }

    public void setVerticaliIndagate(ArrayList verticaliIndagate) {
        this.verticaliIndagate = verticaliIndagate;
    }

    public void setAppoggi(ArrayList appoggi) {
        this.appoggi = appoggi;
    }

    public double getBxElevazione() {
        return bxElevazione;
    }

    public void setBxElevazione(double bxElevazione) {
        this.bxElevazione = bxElevazione;
    }

    public double getBxFonda() {
        return bxFonda;
    }

    public void setBxFonda(double bxFonda) {
        this.bxFonda = bxFonda;
    }

    public double getByElevazione() {
        return byElevazione;
    }

    public void setByElevazione(double byElevazione) {
        this.byElevazione = byElevazione;
    }

    public double getByFonda() {
        return byFonda;
    }

    public void setByFonda(double byFonda) {
        this.byFonda = byFonda;
    }

    public boolean isSpintaVerticale() {
        return spintaVerticale;
    }

    public void setSpintaVerticale(boolean spintaVerticale) {
        this.spintaVerticale = spintaVerticale;
    }

    public boolean isConSpintaAttiva() {
        return conSpintaAttiva;
    }

    public void setConSpintaAttiva(boolean conSpintaAttiva) {
        this.conSpintaAttiva = conSpintaAttiva;
    }

    public double getGammaCLS() {
        return gammaCLS;
    }

    public void setGammaCLS(double gammaCLS) {
        this.gammaCLS = gammaCLS;
    }

    public double getGammaTerreno() {
        return gammaTerreno;
    }

    public void setGammaTerreno(double gammaTerreno) {
        this.gammaTerreno = gammaTerreno;
    }

    public double getH_terreno() {
        return h_terreno;
    }

    public void setH_terreno(double h_terreno) {
        this.h_terreno = h_terreno;
    }

    public double getHPara() {
        return hPara;
    }

    public void setHPara(double para) {
        hPara = para;
    }

    public double getHsElevazione() {
        return hsElevazione;
    }

    public void setHsElevazione(double hsElevazione) {
        this.hsElevazione = hsElevazione;
    }

    public double getK_dinamic() {
        return k_dinamic;
    }

    public void setK_dinamic(double k_dinamic) {
        this.k_dinamic = k_dinamic;
    }

    public double getK_static() {
        /*    if (!k_static_auto) {
            return k_static;
        } else {
            return metodo_Kstatic.getK(fi_Terreno);
        }*/
        return k_static;
    }

    public void setK_static(double k_static) {
        this.k_static = k_static;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList getPalificata() {
        return palificata;
    }

    public void setPalificata(ArrayList palificata) {
        this.palificata = palificata;
    }

    public double getQ_carico() {
        return q_carico;
    }

    public void setQ_carico(double q_carico) {
        this.q_carico = q_carico;
    }

    public double getSpFonda() {
        return spFonda;
    }

    public void setSpFonda(double spFonda) {
        this.spFonda = spFonda;
    }

    public double getSpPara() {
        return spPara;
    }

    public void setSpPara(double spPara) {
        this.spPara = spPara;
    }

    public double getLMuri() {
        return lMuri;
    }

    public void setLMuri(double muri) {
        lMuri = muri;
    }

    public double getSpMuri() {
        return spMuri;
    }

    public void setSpMuri(double spMuri) {
        this.spMuri = spMuri;
    }

    public double getXgElevazione() {
        return xgElevazione;
    }

    public void setXgElevazione(double xgElevazione) {
        this.xgElevazione = xgElevazione;
    }

    public double getYgElevazione() {
        return ygElevazione;
    }

    public void setYgElevazione(double ygElevazione) {
        this.ygElevazione = ygElevazione;
    }

    public ArrayList getCarichi() {
        return carichi;
    }

    public void setCarichi(ArrayList carichi) {
        this.carichi = carichi;
    }

    public ArrayList getCombinazioni() {
        return combinazioni;
    }

    public void setCombinazioni(ArrayList combinazioni) {
        this.combinazioni = combinazioni;
    }

    public double getH_terrenoValle() {
        return h_terrenoValle;
    }

    public void setH_terrenoValle(double valle) {
        h_terrenoValle = valle;
    }

    public double getDeltaTerreno() {
        return deltaTerreno;
    }

    public void setDeltaTerreno(double deltaTerreno) {
        this.deltaTerreno = deltaTerreno;
    }

    public double getX_verifica() {
        return x_verifica;
    }

    public void setX_verifica(double x_verifica) {
        this.x_verifica = x_verifica;
    }

    public double getY_verifica() {
        return y_verifica;
    }

    public void setY_verifica(double y_verifica) {
        this.y_verifica = y_verifica;
    }

    public double getK_static_dinamic() {
        return k_static_dinamic;
    }

    public void setK_static_dinamic(double k_static_dinamic) {
        this.k_static_dinamic = k_static_dinamic;
    }

    public double getDyPara() {
        return dyPara;
    }

    public void setDyPara(double dyPara) {
        this.dyPara = dyPara;
    }

    public double getKpali() {
        return kpali;
    }

    public void setKpali(double kpali) {
        this.kpali = kpali;
    }

    public double getAgS() {
        return agS;
    }

    public void setAgS(double agS) {
        this.agS = agS;
    }
}
