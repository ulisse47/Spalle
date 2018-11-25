/*
 * EasyLexFacade.java
 *
 * Created on 4 ottobre 2003, 15.10
 */
package progetto.presentation.businessDelegate;

import java.awt.Polygon;
//import java.beans.XMLDecoder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import progetto.cache.SpalleCache;
import progetto.model.bean.Appoggio;
import progetto.model.bean.Carico;
import progetto.model.bean.CaricoForze;
import progetto.model.bean.Combinazione;
import progetto.model.bean.Palo;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.model.util.Calcoli;
import progetto.presentation.businessDelegate.stampa.RTFCreator;
import progetto.presentation.util.MyBeanUtils;
import progetto.presentation.view.panel.CaricoCorrentePanel;

import com.lowagie.text.Document;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;
import progetto.model.bean.FondazioneAllaWinkler;
import progetto.model.bean.Terreno;
import progetto.model.bean.Verticale;
import progetto.model.util.dialog.DlgCalcoloAuotmaticoNq;
import progetto.model.util.dialog.DlgWisardPalificata;
import progetto.presentation.commands.SalvaCaricoCorrenteCommand;
import progetto.presentation.commands.SalvaComboCommand;
import progetto.presentation.commands.SalvaFondazioniCommand;
import progetto.presentation.commands.SalvaMuroCommand;
import progetto.presentation.commands.SalvaPalificataCommand;
import progetto.presentation.commands.SalvaVerticaleCorrenteCommand;
import progetto.presentation.view.panel.VerticaleIndagataCorrentePanel;

/**
 * 			
 * 
 * @author Andrea
 */
public class SpalleBusinessDelegateImpl implements SpalleBusinessDelegate {

    private static SpalleBusinessDelegate delegate;
    private int mode;

    private SpalleBusinessDelegateImpl() {
    }

    public static synchronized SpalleBusinessDelegate getInstance() {
        if (delegate == null) {
            delegate = new SpalleBusinessDelegateImpl();
        }
        return delegate;
    }

    /**
     * 
     */
    public synchronized void salvaInputSpalla(Hashtable inputs) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();
        MyBeanUtils.populate(spalla, inputs);
    }

    
    
    /**
     * 
     */
    public synchronized void salvaAppoggi(Object[][] inputs) throws Exception {

        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();
        ArrayList list = new ArrayList();
        Appoggio appoggio;
        Double x, y, z;
        String name;
        for (Object[] input : inputs) {
            name = (String) input[0];
            x = (Double) input[1];
            y = (Double) input[2];
            z = (Double) input[3];
            appoggio = new Appoggio(name, x, y, z);
            boolean add = list.add(appoggio);
        }
        spalla.setAppoggi(list);
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#addAppoggio()
     */
    public void addAppoggio() throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        manager.addAppoggio();
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#deleteAppoggio(int)
     */
    public void deleteAppoggio(Appoggio id_appoggio) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        manager.deleteAppoggio(id_appoggio);
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getCarpenteria()
     */
    public Spalla loadSpallaCorrenteFromModel() {
        SpallaManager manager = SpallaManager.getInstance();
        return manager.getCurrentSpalla();
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#setCaricoCorrente(java.util.Hashtable)
     */
    public ArrayList loadCarichiFromModel() throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        return manager.getCurrentSpalla().getCarichi();
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#setCaricoCorrente(java.util.Hashtable)
     */
    public synchronized void setCaricoCorrente(Carico carico) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        manager.setCurrentCarico(carico);
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#addCarico()
     */
    public void addCarico(String carico) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        manager.addCarico(carico);
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#loadCaricoCorrente()
     */
    public Carico loadCaricoCorrente() throws Exception {
        return SpallaManager.getInstance().getCurrentCarico();
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#salvaCarico(progetto.model.bean.Carico, java.lang.Object[][])
     */
    public synchronized void salvaCaricoCorrente(Object[][] inputs) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        boolean agenteElevazioni = CaricoCorrentePanel.getInstance().getCkAgenteSpalla().isSelected();
        boolean agenteAppoggi = CaricoCorrentePanel.getInstance().getCkAgenteAppoggi().isSelected();
        boolean permanente = CaricoCorrentePanel.getInstance().getCkPermanente().isSelected();
        boolean atrito = CaricoCorrentePanel.getInstance().getCkAtrito().isSelected();

        ArrayList list = new ArrayList();
        Carico carico = manager.getCurrentCarico();
        Double fx, fy, fz, mx, my;
        CaricoForze forze;

        //tipo di carico
        carico.setPermanente(permanente);
        carico.setAgenteSuElevazioni(agenteElevazioni);
        carico.setAgenteSuAppoggi(agenteAppoggi);
        carico.setAtrito(atrito);


        //azioni nella tabella carichi
        for (Object[] input : inputs) {
            fx = (Double) input[1];
            fy = (Double) input[2];
            fz = (Double) input[3];
            mx = (Double) input[4];
            my = (Double) input[5];
            forze = new CaricoForze(fx, fy, fz, mx, my);
            boolean add = list.add(forze);
        }
        carico.setForzeAppoggi(list);
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getFileCorrente()
     */
    public File getFileCorrente() {
        return SpallaManager.getInstance().getFileCorrente();
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#saveToFile(java.lang.String)
     */
    public synchronized void saveToFile(String fileName) throws Exception {
        SpallaManager man = SpallaManager.getInstance();
        Spalla spalla = man.getCurrentSpalla();

        XStream xstream = new XStream();
        String xml = xstream.toXML(spalla);

        BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
        out.write(xml);
        out.close();

        /*XMLEncoder e = new XMLEncoder( new BufferedOutputStream(
        new FileOutputStream( fileName )));
        e.writeObject( spalla );
        e.close();*/

        man.setFileCorrente(new File(fileName));
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#addCombinazione(java.lang.String)
     */
    public void addCombinazione(String nome) throws Exception {
        SpallaManager.getInstance().addCombinazione(nome);
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#salvaCombo(java.lang.Object[][])
     */
    public void salvaCombo(Object[][] inputs) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();
        ArrayList list;
        ArrayList listaComb = new ArrayList();
        Combinazione combo;

        for (Object[] input : inputs) {
            //creo una combinazione
            String nome = (String) input[0];
            combo = new Combinazione();
            combo.setName(nome);
            list = new ArrayList();
            for (int k = 1; k < input.length; k++) {
                boolean add = list.add(input[k]);
            }
            combo.setCombo(list);
            boolean add = listaComb.add(combo);
        }
        spalla.setCombinazioni(listaComb);
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#deleteCarico(int)
     */
    public void deleteCarico(Carico c) throws Exception {
        SpallaManager.getInstance().deleteCarico(c);

    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#loadCombinazioniFromModel()
     */
    public ArrayList loadCombinazioniFromModel() throws Exception {
        return SpallaManager.getInstance().getCurrentSpalla().getCombinazioni();
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#deleteCombinazione(int)
     */
    public void deleteCombinazione(Combinazione c) throws Exception {
        SpallaManager.getInstance().deleteCombinazione(c);
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#openFromFile(java.lang.String)
     */
    public void openFromFile(String fileName) throws Exception {

        SpallaManager man = SpallaManager.getNewInstance();

        //verifica se versione precedente
        try {
            Spalla sp = man.getCurrentSpalla();
            if (sp.getVerticaliIndagate() == null) {
                sp.setVerticaliIndagate(new ArrayList());
            }
            int nc = sp.getCarichi().size();
            ArrayList listcombo = sp.getCombinazioni();
            Combinazione comb = (Combinazione) listcombo.get(0);
            int ncomb = comb.getCombo().size();
            if (ncomb - nc == 5) {
                int ris = JOptionPane.showConfirmDialog(null, "File creato con versione precedente" +
                        "nella verisione attuale sono modificati i coefficeinti di combianzione: continuare ad apire il file? il file verrà aggiornato" +
                        "alla versione attuale", "Attenzione", JOptionPane.YES_NO_OPTION);

                if (ris == JOptionPane.YES_OPTION) {

                    for (int i = 0; i < listcombo.size(); ++i) {
                        comb = (Combinazione) listcombo.get(i);
                        comb.getCombo().add(3, new Double(1));
                    }
                } else {

                    return;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        BufferedReader in = new BufferedReader(new FileReader(fileName));
        StringBuffer xml = new StringBuffer();
        String text = null;
        while ((text = in.readLine()) != null) {
            xml.append(text);
        }
        in.close();

        XStream xstream = new XStream();
        man.setCurrentSpalla((Spalla) xstream.fromXML(xml.toString()));

        man.setFileCorrente(new File(fileName));
    }

    public void openFromResource(InputStream input) throws Exception {

        SpallaManager man = SpallaManager.getNewInstance();

        XStream xstream = new XStream();
        Spalla sp = (Spalla) xstream.fromXML(input);

        man.setCurrentSpalla(sp);

    /*XMLDecoder e = new XMLDecoder( input );
    man.setCurrentSpalla( ( Spalla ) e.readObject() );
    //man.setFileCorrente( null );
    e.close();*/
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#addPalo()
     */
    public void addPalo() throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        manager.addPalo();
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#salvaPalificata(java.lang.Object[][])
     */
    public void salvaPalificata(Object[][] inputs) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        Spalla spalla = manager.getCurrentSpalla();

        ArrayList listaPali = new ArrayList();
        Palo palo;
        Double x, y, diametro;

        for (Object[] input : inputs) {
            //creo un palo
            x = (Double) input[1];
            y = (Double) input[2];
            diametro = (Double) input[3];
            palo = new Palo(x, y, diametro);
            listaPali.add(palo);
        }
        spalla.setPalificata(listaPali);
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#loadPalificataFromModel()
     */
    public ArrayList loadPalificataFromModel() {
        return SpallaManager.getInstance().getCurrentSpalla().getPalificata();
    }

    /*  public ArrayList loadTerreniFromModel() {
    ArrayList vert= SpallaManager.getInstance().getArrayCurrentVerticale();
    return vert;
    }
     */
    public ArrayList loadVerticaliFromModel() {
        ArrayList vert = SpallaManager.getInstance().getVerticaliIndagate();
        return vert;
    }
    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#deletePalo(int)
     */

    public void deletePalo(int id_combo) throws Exception {
        SpallaManager.getInstance().deletePalo(id_combo);

    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#loadComboCorrente()
     */
    public Combinazione loadComboCorrente() throws Exception {
        return SpallaManager.getInstance().getCurrentCombinazione();
    }

    /**
     * Return -1 se non è selezionato nessuna combinazione
     */
    public int loadIdComboCorrente() {
        return SpallaManager.getInstance().getCurrentIdCombo();
    }


    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#setComboCorrente(progetto.model.bean.Combinazione)
     */
    public void setComboCorrente(int combinazione) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        manager.setCurrentCombinazione(combinazione);
    }

    public void setFileCorrente(File file) {
        SpallaManager manager = SpallaManager.getInstance();
        manager.setFileCorrente(file);
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#calculate_sigma_wx_wy()
     */
    public double[] get_sigma_wx_wy() {

        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        int id_combo = loadIdComboCorrente();
        int nPali = loadPalificataFromModel().size();
        double[] sigmaWx;

        Calcoli calcoli = new Calcoli();
        if (nPali == 0 && id_combo != -1) {
            cachedValue = cache.getValue(SpalleCache.sigma_wx_wy);
            if (cachedValue == null) {
                sigmaWx = calcoli.get_sigma_wx_wy(id_combo);
                cache.addValue(sigmaWx, SpalleCache.sigma_wx_wy);
            } else {
                sigmaWx = (double[]) cachedValue;
            }
            return sigmaWx;
        } else {
            sigmaWx = new double[3];
        }
        return sigmaWx;
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getMverifica()
     */
    public double[][] getMverifica() throws Exception {
        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        int id_combo = loadIdComboCorrente();
        Calcoli calcoli = new Calcoli();
        double[][] mverifica = null;
        if (id_combo != -1) {
            cachedValue = cache.getValue(SpalleCache.mverifica);
            if (cachedValue == null) {
                mverifica = calcoli.getMverifica(id_combo);
                cache.addValue(mverifica, SpalleCache.mverifica);
            } else {
                mverifica = (double[][]) cachedValue;
            }

            return mverifica;
        } else {
            return new double[4][2];
        }
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getMverifica()
     */
    public double[] getM_V_SingoloPalo() throws Exception {
        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        int id_combo = loadIdComboCorrente();
        Calcoli calcoli = new Calcoli();
        double[] mverifica = null;
        if (id_combo != -1) {
            cachedValue = cache.getValue(SpalleCache.mverifica);
            if (cachedValue == null) {
                mverifica = calcoli.getM_V_SingoloPalo(id_combo);
                cache.addValue(mverifica, SpalleCache.mverifica);
            } else {
                mverifica = (double[]) cachedValue;
            }

            return mverifica;
        } else {
            return new double[2];
        }
    }
    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getNpalificata()
     */

    public double[] getNpalificata() {
        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        double[] npalificata = null;
        int id_combo = loadIdComboCorrente();
        int nPali = loadPalificataFromModel().size();
        Calcoli calcoli = new Calcoli();
        if (nPali != 0 && id_combo != -1) {
            cachedValue = cache.getValue(SpalleCache.npalificata);
            if (cachedValue == null) {
                npalificata = calcoli.getNpalificata(id_combo);
                cache.addValue(npalificata, SpalleCache.npalificata);
            } else {
                npalificata = (double[]) cachedValue;
            }

            return npalificata;
        } else {
            return new double[nPali];
        }
    }

    public double[] getNpalificata(int id_combo) {

        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        double[] npalificata = null;

        //int id_combo = loadIdComboCorrente();

        int nPali = loadPalificataFromModel().size();
        Calcoli calcoli = new Calcoli();
        if (nPali != 0 && id_combo != -1) {
            cachedValue = cache.getValue(id_combo + SpalleCache.npalificata);
            if (cachedValue == null) {
                npalificata = calcoli.getNpalificata(id_combo);
                cache.addValue(npalificata, id_combo + SpalleCache.npalificata);
            } else {
                npalificata = (double[]) cachedValue;
            }

            return npalificata;
        } else {
            return new double[nPali];
        }
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getM1Combo()
     */
    public double[] getM1Combo() {
        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        double[] value = null;
        int id_combo = loadIdComboCorrente();
        //int nPali = loadPalificataFromModel().size();
        Calcoli calcoli = new Calcoli();
        if ( /*nPali == 0 && */id_combo != -1) {
            cachedValue = cache.getValue(SpalleCache.m1Combo);
            if (cachedValue == null) {
                value = calcoli.getM1Combo(id_combo);
                cache.addValue(value, SpalleCache.m1Combo);
            } else {
                value = (double[]) cachedValue;
            }
            return value;
        } else {
            return new double[5];
        }
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getM1Combo(int)
     */
    public double[] getM1Combo(int id) {
        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        String key = String.valueOf(id + "1");

        cachedValue = cache.getValue(key);
        if (cachedValue == null) {
            value = calcoli.getM1Combo(id);
            cache.addValue(value, key);
        } else {
            value = (double[]) cachedValue;
        }

        return value;
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getM1Combo(int)
     */
    public double[] getM1Spalla() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1Spalla();
        return value;
    }

    public double[][] getMMensole(int i) {
        double[][] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getMverifica(i);

        return value;
    }

    public double[] getM2Spalla() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2Spalla();
        return value;
    }

    public double[] getM1Paraghiaia() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1Paraghiaia();
        return value;
    }

    public double[] getM2Paraghiaia() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2Paraghiaia();
        return value;
    }

    public double[] getM1Sovraccarico(boolean sismica) {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1sovraccarico(sismica);
        return value;
    }

    public double[] getM2SovraccaricoV() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2SovraccaricoV();
        return value;
    }

    public double[] getM2SovraccaricoH(boolean sismica) {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2SovraccaricoH(sismica);
        return value;
    }

    public double[] getM1SpintaStaticaH(boolean sismica) {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1TerrenoMonteH(sismica);
        return value;
    }

    public double[] getM2SpintaStatica(boolean sismica) {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2TerrenoMonteH(sismica);
        return value;
    }

    public double[] getM1Permanenti() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1Permanenti();
        return value;
    }

    public double[] getM2Permanenti() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2Permanenti();
        return value;
    }

    public double[] getM1SpinteDianamicheSismaY() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1SpinteDianamicheSismaY();
        return value;
    }

    public double[] getM2SpinteDianamicheSismaY() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2SpinteDianamicheSismaY();
        return value;
    }

    public double[] getM1SpinteDianamicheSismaX() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1SpinteDianamicheSismaX();
        return value;
    }

    public double[] getM2SpinteDianamicheSismaX() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2SpinteDianamicheSismaX();
        return value;
    }

    public double[] getM1CaricoAppoggi(int i) {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1CarichiAppoggio(i);
        return value;
    }

    public double[] getFyCaricoAppoggiAtrito() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getFyCaricoAppoggiAtrito();
        return value;
    }

    public double[] getM2CaricoAppoggi(int i) {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2CarichiAppoggio(i);
        return value;
    }

    public double[] getM1MuriAla() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1MuriAla();
        return value;
    }

    public double[] getM2MuriAla() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2MuriAla();
        return value;
    }

    public double[] getM1Ciabatta() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1CiabattaFondazione();
        return value;
    }

    public double[] getM2Ciabatta() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2CiabattaFondazione();
        return value;
    }

    public double[] getM1TerrenoMonte() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1TerrenoMonteV();
        return value;
    }

    public double[] getM2TerrenoMonteV() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2TerrenoMonteV();
        return value;
    }

    public double[] getM1TerrenoValle() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM1TerrenoValle();
        return value;
    }

    public double[] getM2TerrenoValle() {
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getM2TerrenoValle();
        return value;
    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#getM2Combo(int)
     */
    public double[] getM2Combo(int id) {
        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        double[] value = null;
        Calcoli calcoli = new Calcoli();
        String key = String.valueOf(id + "2");

        cachedValue = cache.getValue(key);
        if (cachedValue == null) {
            value = calcoli.getM2Combo(id);
            cache.addValue(value, key);
        } else {
            value = (double[]) cachedValue;
        }

        return value;

    }

    public double[][] getMAppoggiCombo(int id) {
        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        double[][] value = null;
        Calcoli calcoli = new Calcoli();
        String key = String.valueOf(id + "3");

        cachedValue = cache.getValue(key);
        if (cachedValue == null) {
            value = calcoli.getMappoggiCombo(id);
            cache.addValue(value, key);
        } else {
            value = (double[][]) cachedValue;
        }

        return value;

    }

    public double[][] getMAppoggiPermanentiCombo(int id) {
        SpalleCache cache = SpalleCache.getInstance();
        Object cachedValue = null;
        double[][] value = null;
        Calcoli calcoli = new Calcoli();
        String key = String.valueOf(id + "3");

        cachedValue = cache.getValue(key);
        if (cachedValue == null) {
            value = calcoli.getMappoggiPemanentiCombo(id);
            cache.addValue(value, key);
        } else {
            value = (double[][]) cachedValue;
        }

        return value;

    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#elabora()
     */
    public void elabora() {
        //@todo: calcolare tutte le valriabili necessarie al calcolo
        //e salvarle in una classe di cache.
        SpalleCache cache = SpalleCache.getInstance();
        SpallaManager man = SpallaManager.getInstance();
        boolean ck = man.checkStartiTerreno();
        if (ck == false) {
            return;
        }
        cache.refreshValue();

        //calcolo palificata?
        double[][] Ftesta = new double[2][1];
        Ftesta[0][0] = 0;
        Ftesta[1][0] = 5000;
        double Lp = man.getCurrentSpalla().getLpalo();
        double Dp = man.getCurrentSpalla().getDpalo();

        FondazioneAllaWinkler wk = new FondazioneAllaWinkler(100, Ftesta,
                Lp, Dp,false);

        man.setSPOST_NODI(wk.getROTAZ());
        man.setMEXT(wk.getMEXT());
        man.setPRESS_NODI(wk.getPRESS());

    }

    /* (non-Javadoc)
     * @see progetto.presentation.businessDelegate.SpalleBusinessDelegate#createPoligonoPressioni()
     */
    public Polygon createPoligonoPressioni() {
        Calcoli calcoli = new Calcoli();
        int id_combo = loadIdComboCorrente();
        if (id_combo == -1) {
            return new Polygon();
        }
        return calcoli.getPoligonoPressioni(id_combo);
    }

    public void stampa(String fileName) throws Exception {

        try {
            Document doc = RTFCreator.creaRTFDocument(fileName);
        //Document doc = RTFCreator.creaRTFDocument("stampa.rtf");
        //openRTFDocument("stampa.rtf" /*"c:/rtf.rtf"*/);
        } catch (Exception io) {
            io.printStackTrace();
        }
    //}
    }

    public void openRTFDocument(String fileName) throws IOException {
        Process echoProcess = null;
        try {
            echoProcess = Runtime.getRuntime().exec("cmd /C  \"" + fileName + "\""); //crea un Thread di tipo Process
        } catch (IOException ioEx) {
            throw new IOException("Could not retrieve env variables " + ioEx.toString());
        }
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void wizardPalificata() throws Exception {

        DlgWisardPalificata dlg = new DlgWisardPalificata();
        dlg.setVisible(true);

    }

    public void addStratoTerreno() throws Exception {

        SpallaManager manager = SpallaManager.getInstance();
        manager.addStratoTerreno();

    }

    public void creaPalificataInAutomatico(double ix, double iy, int nx, int ny, double diametro) {
        SpallaManager manager = SpallaManager.getInstance();
        manager.creaPalificataInAutomatico(ix, iy, nx, ny, diametro);


//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void moveUpCaricoCorrente(int index) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        manager.moveUpCaricoCorrente(index);


    //        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void moveDownCaricoCorrente(int index) throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        manager.moveDownCaricoCorrente(index);
    }

    public void eliminaTuttiPali() {
        SpallaManager manager = SpallaManager.getInstance();
        manager.eliminaTuttiPali();
    }

    public void removeStratoTerreno(int id_strato) {

        SpallaManager.getInstance().deleteStrato(id_strato);
    }

    public void salvaVerticaleCorrente(Object[][] rowData) {

        Verticale curV = SpallaManager.getInstance().getCurrentVerticale();

        if (curV == null) {
            return;
        }

        String Nctx = VerticaleIndagataCorrentePanel.getInstance().getTxtNc().getText();
        String Nqtx = VerticaleIndagataCorrentePanel.getInstance().getTxtNq().getText();

        String Lpalotx = VerticaleIndagataCorrentePanel.getInstance().getTxtLpalo().getText();
        String Zpalotx = VerticaleIndagataCorrentePanel.getInstance().getTxtZtestaPalo().getText();
        String Dpalotx = VerticaleIndagataCorrentePanel.getInstance().getTxtDPalo().getText();
        String Zfladatx = VerticaleIndagataCorrentePanel.getInstance().getTxtZfalda().getText();


        double Nc = 0;
        double Nq = 0;
        double Lpalo = 0;
        double Zpalo = 0;
        double Dpalo = 0;
        double Zfalda = 0;

        try {
            Nc = Double.parseDouble(Nctx);
            Nq = Double.parseDouble(Nqtx);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "valori non numeri di Nc o Nq",
                    "Attenzione", JOptionPane.WARNING_MESSAGE);
            Exceptions.printStackTrace(ex);
            return;
        }

        try {
            Lpalo = Double.parseDouble(Lpalotx);
            Zpalo = Double.parseDouble(Zpalotx);
            Dpalo = Double.parseDouble(Dpalotx);
            Zfalda = Double.parseDouble(Zfladatx);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "valori non numeri di Lpalo, quota o diamtro palo",
                    "Attenzione", JOptionPane.WARNING_MESSAGE);
            Exceptions.printStackTrace(ex);
            return;
        }

        SpallaManager.getInstance().getCurrentSpalla().setLpalo(Lpalo);
        SpallaManager.getInstance().getCurrentSpalla().setZpalo(Zpalo);
        SpallaManager.getInstance().getCurrentSpalla().setDpalo(Dpalo);
        SpallaManager.getInstance().getCurrentSpalla().setZfalda(Zfalda);

        curV.setNc(Nc);
        curV.setNq(Nq);

        ArrayList listaTerreni = new ArrayList();
        Terreno terreno;
        Double h, fi, g, cu, k, alfa;

        for (Object[] rowData1 : rowData) {
            //creo un palo
            h = (Double) rowData1[1];
            fi = (Double) rowData1[2];
            g = (Double) rowData1[3];
            cu = (Double) rowData1[4];
            k = (Double) rowData1[5];
            alfa = (Double) rowData1[6];
            terreno = new Terreno(h, fi, g, cu, k, alfa);
            boolean add = listaTerreni.add(terreno);
        }
        curV.setStrati(listaTerreni);

    }

    public void addVerticaleIndagata(String name) {
        SpallaManager manager = SpallaManager.getInstance();
        manager.addVerticaleIndagata(name);


    }

    public void deleteVerticaleIndagata(Verticale vert) {
        SpallaManager.getInstance().deleteVerticaleIndagata(vert);
    }

    public double calcolaQl1Strati(Verticale verticale) {
        double value;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getQl1Starti(verticale);
        return value;
    }

    public double getCsi3() {
        int n = SpallaManager.getInstance().getVerticaliIndagate().size();

        if (n == 0) {
            return Double.MAX_VALUE;
        }
        if (n == 1) {
            return 1.7;
        }
        if (n == 2) {
            return 1.65;
        }
        if (n == 3) {
            return 1.60;
        }
        if (n == 4) {
            return 1.55;
        }
        if (n == 5) {
            return 1.50;
        }
        if (n == 6) {
            return 1.50;
        }
        if (n == 7) {
            return 1.45;
        }
        if (n == 8) {
            return 1.45;
        }
        if (n == 9) {
            return 1.45;
        }
        if (n >= 10) {
            return 1.4;
        }

        return 1;

    }

    public double getCsi4() {
        int n = SpallaManager.getInstance().getVerticaliIndagate().size();

        if (n == 0) {
            return Double.MAX_VALUE;
        }
        if (n == 1) {
            return 1.7;
        }
        if (n == 2) {
            return 1.55;
        }
        if (n == 3) {
            return 1.48;
        }
        if (n == 4) {
            return 1.42;
        }
        if (n == 5) {
            return 1.34;
        }
        if (n == 6) {
            return 1.34;
        }
        if (n == 7) {
            return 1.28;
        }
        if (n == 8) {
            return 1.28;
        }
        if (n == 9) {
            return 1.28;
        }
        if (n >= 10) {
            return 1.21;
        }

        return 1;

    }

    public double gammaR1_portataLaterale() {
        return 1.0;
    }

    public double gammaR2_portataLaterale() {
        return 1.45;
    }

    public double gammaR3_portataLaterale() {
        return 1.15;
    }

    public double gammaR1_portataLateraleTraz() {
        return 1.0;
    }

    public double gammaR2_portataLateraleTraz() {
        return 1.6;
    }

    public double gammaR3_portataLateraleTraz() {
        return 1.25;
    }

    public double calcolaPesoPalo(Boolean consideroSottospinteIdrauliche) {
        Spalla sp = SpallaManager.getInstance().getCurrentSpalla();
        double Zfalda = sp.getZfalda();
        double Zbase = sp.getZpalo();
        double L = sp.getLpalo();
        double d = sp.getDpalo();
        double a = Math.PI * d * d / 4;
        double p = a * 25 * L;
        if (consideroSottospinteIdrauliche == true) {
            double Zpunta = Zbase + L;
            if (Zpunta > Zfalda) {
                p = p - (Zpunta - Zfalda) * 9.8 * a;
            }
        }
        return p;


    }

    public double calcolaQBaseStrati(Verticale verticale) {
        double value;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getQbaseStarti(verticale);
        return value;

    }

    public double calcolaCuMedioTestaPalo(Verticale verticale) {
        double value;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getCuMediaPuntaPalo(verticale);
        return value;

    }

    public double calcolaFiMedioTestaPalo(Verticale verticale) {
        double value;
        Calcoli calcoli = new Calcoli();
        value = calcoli.getFiMedioPuntaPalo(verticale);
        return value;

    }

    public double gammaR1_portataBase() {
        return 1.0;
    }

    public double gammaR2_portataBase() {
        return 1.7;
    }

    public double gammaR3_portataBase() {
        return 1.35;
    }

    public void setVerticaleCorrente(Verticale vcur) {
        SpallaManager.getInstance().setCurrentVerticale(vcur);
    }

    public ArrayList loadAppoggiFromModel() {
        SpallaManager manager = SpallaManager.getInstance();
        return manager.getCurrentSpalla().getAppoggi();
    }

    public void salvaInLocale() throws Exception {
        new SalvaCaricoCorrenteCommand().execute(null);
        new SalvaComboCommand().execute(null);
        new SalvaFondazioniCommand().execute(null);
        new SalvaMuroCommand().execute(null);
        new SalvaPalificataCommand().execute(null);
        new SalvaVerticaleCorrenteCommand().execute(null);

    }

    public void calcolaNqBerezantzev() throws Exception {
        SpallaManager manager = SpallaManager.getInstance();
        if (manager.checkStartiTerreno() == false) {
            return;
        }
        DlgCalcoloAuotmaticoNq dlg = new DlgCalcoloAuotmaticoNq();
        dlg.setVisible(true);

    }

    public void azzeraTutto() {
        SpallaManager manager = SpallaManager.getInstance();
        Spalla sp = manager.getCurrentSpalla();

        manager.setCurrentCarico(null);
        manager.setCurrentVerticale(null);
        manager.setCurrentCombinazione(-1);
//        sp.getAppoggi().clear();

    }

    public void setPathCorrente(String absolutePath) {
               SpallaManager manager = SpallaManager.getInstance();
               manager.setPathCorrente(absolutePath);
    }

    public String getPathCorrente() {
               SpallaManager manager = SpallaManager.getInstance();
               return manager.getPathCorrente();
    }

   
}

