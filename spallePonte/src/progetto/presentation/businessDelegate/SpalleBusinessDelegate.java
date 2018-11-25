package progetto.presentation.businessDelegate;

import java.awt.Polygon;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import progetto.model.bean.Appoggio;
import progetto.model.bean.Carico;
import progetto.model.bean.Combinazione;
import progetto.model.bean.Spalla;
import progetto.model.bean.Verticale;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 13-set-2003
 * Time: 16.38.45
 * To change this template use Options | File Templates.
 */
public interface SpalleBusinessDelegate {

    public void addVerticaleIndagata(String name);

    public void azzeraTutto();

    public void calcolaNqBerezantzev() throws Exception;

    public double calcolaPesoPalo(Boolean consideroSottospinteIdrauliche);
         public double calcolaFiMedioTestaPalo(Verticale verticale);
   
    public double calcolaCuMedioTestaPalo(Verticale verticale);
    public double calcolaQBaseStrati(Verticale verticale);

    public double calcolaQl1Strati(Verticale verticale);

    public void deleteVerticaleIndagata(Verticale vert);

    public double gammaR1_portataBase();

    public double gammaR2_portataBase();

    public double gammaR3_portataBase();

    public double gammaR1_portataLaterale();

    public double gammaR2_portataLaterale();

    public double gammaR3_portataLaterale();

    public double gammaR1_portataLateraleTraz();

    public double gammaR2_portataLateraleTraz();

    public double gammaR3_portataLateraleTraz();

    public void salvaInLocale()  throws Exception;

    public double getCsi3();

    public double getCsi4();

    public int getMode();

    public ArrayList loadAppoggiFromModel();

    public void removeStratoTerreno(int id_strato);

    /**
     * @param inputs
     */
    public void salvaInputSpalla(Hashtable inputs) throws Exception;

    /**
     * @param rowData
     */
    public void salvaAppoggi(Object[][] rowData) throws Exception;

    /**
     * 
     */
    public void addAppoggio() throws Exception;

    public void salvaVerticaleCorrente(Object[][] rowData);

    public void setMode(int mode);

    public void creaPalificataInAutomatico(double ix, double iy, int nx, int ny, double diametro);

    public void eliminaTuttiPali();

    public void setVerticaleCorrente(Verticale nvert);

    public void stampa(String file) throws Exception;

    /**
     * @param id_appoggio
     */
    public void deleteAppoggio(Appoggio id_appoggio) throws Exception;

    /**
     * @param inputs
     */
    public Spalla loadSpallaCorrenteFromModel();

    /**
     * @param inputs
     */
    public ArrayList loadCarichiFromModel() throws Exception;

    public ArrayList loadVerticaliFromModel() throws Exception;

    /**
     * @param carico
     */
    public void setCaricoCorrente(Carico carico) throws Exception;

    /**
     * @param index
     */
    public void moveUpCaricoCorrente(int index) throws Exception;

    /**
     * @param index
     */
    public void moveDownCaricoCorrente(int index) throws Exception;

    /**
     * @param nome
     * 
     */
    public void addCarico(String nome) throws Exception;

    /**
     * @return
     */
    public Carico loadCaricoCorrente() throws Exception;

    /**
     * @param caricoCorrente
     * @param rowData
     */
    public void salvaCaricoCorrente(Object[][] rowData) throws Exception;

    /**
     * @return
     */
    public File getFileCorrente();

    public void setFileCorrente(File file);

    /**
     * @param fileName
     */
    public void saveToFile(String fileName) throws Exception;

    /**
     * @param nome
     */
    public void addCombinazione(String nome) throws Exception;

    /**
     * @param rowData
     */
    public void salvaCombo(Object[][] rowData) throws Exception;

    /**
     * @param id_carico
     */
    public void deleteCarico(Carico c) throws Exception;

    /**
     * @return
     */
    public ArrayList loadCombinazioniFromModel() throws Exception;

    /**
     * @param id_combo
     */
    public void deleteCombinazione(Combinazione c) throws Exception;

    /**
     * @param fileName
     */
    public void openFromFile(String fileName) throws Exception;

    public void openFromResource(InputStream input) throws Exception;

    /**
     * 
     */
    public void addPalo() throws Exception;

    /**
     * 
     */
    public void addStratoTerreno() throws Exception;

    /**
     * 
     */
    public void wizardPalificata() throws Exception;

    /**
     * @param rowData
     */
    public void salvaPalificata(Object[][] rowData) throws Exception;

    /**
     * @return
     */
    public ArrayList loadPalificataFromModel();

    /**
     * @param id_combo
     */
    public void deletePalo(int id_combo) throws Exception;

    /**
     * @return
     */
    public Combinazione loadComboCorrente() throws Exception;

    public int loadIdComboCorrente() throws Exception;

    /**
     * @param combinazione
     */
    public void setComboCorrente(int combinazione) throws Exception;

    /**
     * @return
     */
    public double[][] getMverifica() throws Exception;

    /**
     * @return
     */
    public double[] getM_V_SingoloPalo() throws Exception;

    /**
     * @return
     */
    public double[] getNpalificata();

    public double[] getNpalificata(int id_combo);

    /**
     * @return
     */
    public double[] get_sigma_wx_wy();

    /**
     * @return
     */
    public double[] getFyCaricoAppoggiAtrito();

    /**
     * @return
     */
    public double[] getM1Combo();

    /**
     * @param id
     * @return
     */
    public double[] getM1Combo(int id);

    /**
     * @param id
     * @return
     */
    public double[] getM2Combo(int id);

    /**
     * @param id
     * @return
     */
    public double[][] getMAppoggiPermanentiCombo(int id);

    /**
     * @param id
     * @return
     */
    public double[][] getMAppoggiCombo(int id);

    /**
     * 
     */
    public void elabora();

    /**
     * @return
     */
    public Polygon createPoligonoPressioni();

    public void setPathCorrente(String absolutePath);

    public String getPathCorrente();
}
