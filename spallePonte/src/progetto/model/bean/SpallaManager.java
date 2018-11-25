/*
 * Created on 3-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.model.bean;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import progetto.exception.NessunAppoggioDefinitoException;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SpallaManager {

    private PalificataWizard_Set palificataWS;
    private File fileCorrente;
   
    private String pathCorrente;
    
    private static SpallaManager muroManager;
    /**
     * il muro corrente
     */
    private Spalla currentSpalla;
    private Carico currentCarico;
    private Verticale currentVerticale;

    public PalificataWizard_Set getPalificataWS() {
        if (palificataWS == null) {
            palificataWS = new PalificataWizard_Set();
        }
        return palificataWS;

    }

    public void setPalificataWS(PalificataWizard_Set palificataWS) {
        this.palificataWS = palificataWS;
    }

    public double[][] getFORZM() {
        return FORZM;
    }

    public void setFORZM(double[][] FORZM) {
        this.FORZM = FORZM;
    }

    public double[][][] getMEXT() {
        return MEXT;
    }

    public void setMEXT(double[][][] MEXT) {
        this.MEXT = MEXT;
    }

    public double[][] getPRESS_NODI() {
        return PRESS_NODI;
    }

    public void setPRESS_NODI(double[][] PRESS_NODI) {
        this.PRESS_NODI = PRESS_NODI;
    }

    public double[][][] getSPOST_NODI() {
        return SPOST_NODI;
    }

    public void setSPOST_NODI(double[][][] SPOST_NODI) {
        this.SPOST_NODI = SPOST_NODI;
    }

    //azioni sui pali alla winkler
    public double[][][] MEXT; //momenti alle estremità delle travi
    public double[][] FORZM; //reazioni molle
    public double[][][] SPOST_NODI; // SPOSTAMENTI NODI
    public double[][] PRESS_NODI; //pressioni terreno

    //  private static Color[] colori = new Color[30];
    public Verticale getCurrentVerticale() {
        ArrayList verticali = getCurrentSpalla().getVerticaliIndagate();
        if (currentVerticale == null & verticali.size() != 0) {
            currentVerticale = (Verticale) verticali.get(0);
        }
        return currentVerticale;
    }

     public String getPathCorrente() {
        return pathCorrente;
    }

    public void setPathCorrente(String pathCorrente) {
        this.pathCorrente = pathCorrente;
    }
    
    public void setNameCurrentCarico(String nome) {
        currentCarico.setName(nome);
    }

    public void setNameCurrentVerticale(String nome) {
        currentVerticale.setName(nome);
    }

    public double getHmaxTerreni() {
        ArrayList st = currentVerticale.getStrati();
        int n = st.size();
        Terreno t = (Terreno) st.get(n - 1);
        t.getH();
        return t.getH() + currentSpalla.getZpalo();
    }

    public boolean checkStartiTerreno() {
        Spalla sp = getCurrentSpalla();
        ArrayList ver = sp.getVerticaliIndagate();
        int nv = ver.size();
        String msg;

        //ciclo per le verticali
        for (int i = 0; i < nv; ++i) {
            Verticale v = (Verticale) ver.get(i);
            ArrayList str = v.getStrati();
            int ns = str.size();

            //ciclo per strati
            //..strato 0
            Terreno t = (Terreno) str.get(0);
            double ht = t.getH();
            if (ht < 0) {
                msg = "Lo strato di terreno num. 0 della verticale '"
                        + v.getName() + "' deve avere quota H maggiore di zero";
                JOptionPane.showMessageDialog(null, msg,
                        "Check dati", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            //altri strati
            for (int j = 1; j < ns; ++j) {
                t = (Terreno) str.get(j);
                double hj = t.getH();
                if (hj < ht) {
                    msg = "Lo strato di terreno num. " + Integer.toString(j)
                            + " della verticale '" + v.getName() + "' deve avere quota H maggiore"
                            + " dello starto sottostante";
                    JOptionPane.showMessageDialog(null, msg,
                            "Check dati", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                ht = hj;
            }
            //fine controllo h strati
            //controllo altezza minima terreno
            t = (Terreno) str.get(ns - 1);
            double Hmax = t.getH();
            double Hpunta = sp.getZpalo() + sp.getLpalo();
            double dp = sp.getDpalo();
            if (Hmax < Hpunta + 5 * dp) {
                msg = "La stratigrafia della verticale '" + v.getName()
                        + "' deve estendersi oltre la base del palo per almeno 5 volte il diametro ";
                JOptionPane.showMessageDialog(null, msg,
                        "Check dati", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }

        return true;
    }

    public void setCurrentVerticale(Verticale currentVerticale) {
        this.currentVerticale = currentVerticale;
    }
    private int currentCombinazione = -1;

    /*   public static Color[] getColori() {
    return colori;
    }
     */
    /**
     *
     */
    private SpallaManager() {

        /*        for (int i = 0; i<30;++i){
    colori[i]=Color.getHSBColor((float)Math.random(), 
    (float)Math.random(), (float)Math.random());    
    }*/
    }

    /**
     *
     * @return
     */
    public static synchronized SpallaManager getInstance() {
        if (muroManager == null) {
            muroManager = new SpallaManager();
        }

        return muroManager;
    }

    public static synchronized SpallaManager getNewInstance() {
        muroManager = new SpallaManager();
        return muroManager;
    }

    public void addStratoTerreno() {

        ArrayList vert = getCurrentSpalla().getVerticaliIndagate();
        if (vert.isEmpty()) {
            Verticale nv = new Verticale("V0");
            vert.add(nv);
            setCurrentVerticale(nv);
        } else {
            Terreno ter = new Terreno();
            if (currentVerticale == null) {
                currentVerticale = (Verticale) vert.get(0);
            }
            boolean add = currentVerticale.strati.add(ter);

        }

    }

    public void addVerticaleIndagata(String name) {

        ArrayList vert = getCurrentSpalla().getVerticaliIndagate();
        Verticale verticale = new Verticale(name);
        boolean add = vert.add(verticale);
        setCurrentVerticale(verticale);
    }

    public void deleteVerticaleIndagata(Verticale vert) {
        Spalla sp = getCurrentSpalla();
        ArrayList v = sp.getVerticaliIndagate();
        v.remove(vert);
        if (v.size() != 0) {
            setCurrentVerticale((Verticale) v.get(0));
        } else {
            setCurrentVerticale(null);
        }

    }

    /*  public ArrayList getArrayCurrentVerticale(){
    if (currentVerticale != -1){
    return (ArrayList)getCurrentSpalla().getVerticaliIndagate().get(currentVerticale);
    }
    return null;
    }
     */
    public void deleteStrato(int id_strato) {
        Verticale v = getCurrentVerticale();
        v.strati.remove(id_strato);
    }

    public void eliminaTuttiPali() {
        ArrayList pali = getCurrentSpalla().getPalificata();
        if (pali.size() != 0) {
            int r = JOptionPane.showConfirmDialog(null, "Eliminare tutti i pali?");
            if (r == JOptionPane.OK_OPTION) {
                pali.clear();
            }

        }
    }

    /**
     *
     * @return
     */
    public Spalla getCurrentSpalla() {
        if (currentSpalla == null) {
            currentSpalla = new Spalla();
        }

        return currentSpalla;
    }

    public void moveDownCaricoCorrente(int index) {
        ArrayList carichi = getCurrentSpalla().getCarichi();
        int nc = carichi.size();
        if (index == nc - 1) {
            return;
        }

        Carico c = (Carico) carichi.get(index);
        carichi.remove(index);
        carichi.add(index + 1, c);

        CombinazioniManager.getInstance().moveDownCarico(index, getCurrentSpalla().getCombinazioni());

    }

    public void moveUpCaricoCorrente(int index) {

        if (index == 0) {
            return;
        }

        ArrayList carichi = getCurrentSpalla().getCarichi();
        Carico c = (Carico) carichi.get(index);
        carichi.remove(index);
        carichi.add(index - 1, c);

        CombinazioniManager.getInstance().moveUpCarico(index, getCurrentSpalla().getCombinazioni());

        //   throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setCurrentSpalla(Spalla currentSpalla) {
        this.currentSpalla = currentSpalla;
    }

    /**
     *
     * @param nome
     * @return
     */
    public Spalla creaSpalla(
            String nome) {
        currentSpalla = new Spalla();
        currentSpalla.setNome(nome);
        return currentSpalla;
    }

    /**
     * Salva il muro
     *
     * @throws Exception
     */
    public void salvaSpalla() throws Exception {
        // TODO: da implementare
    }

    /**
     *
     */
    public void addAppoggio() {
        Spalla spalla = getCurrentSpalla();
        boolean add = spalla.getAppoggi().add(new Appoggio());
        CaricoManager man = CaricoManager.getInstance();
        man.addAppoggio(spalla.getCarichi());
    }

    /**
     *
     */
    public void addAppoggio(double x, double y, double z) {
        boolean add = getCurrentSpalla().getAppoggi().add(new Appoggio("ap", x, y, z));
        CaricoManager man = CaricoManager.getInstance();
        man.addAppoggio(getCurrentSpalla().getCarichi());
    }

    /**
     * @param id_appoggio
     */
    public void deleteAppoggio(Appoggio id_appoggio) {
        ArrayList list = (ArrayList) getCurrentSpalla().getAppoggi();
        list.remove(id_appoggio);
        CaricoManager man = CaricoManager.getInstance();
        man.deleteAppoggio(getCurrentSpalla().getCarichi(), id_appoggio);
    }

    /**
     *
     * @param nome
     * @throws NessunAppoggioDefinitoException
     */
    public void addCarico(String nome) throws NessunAppoggioDefinitoException {
        int appoggi = currentSpalla.getAppoggi().size();
        if (appoggi == 0) {
            throw new NessunAppoggioDefinitoException();
        }

        Carico carico = new Carico(nome, appoggi);
        boolean add = getCurrentSpalla().getCarichi().add(carico);
        setCurrentCarico(carico);

        CombinazioniManager.getInstance().addCarico(
                getCurrentSpalla().getCombinazioni());

    }

    public Carico getCurrentCarico() {
        ArrayList carichi = getCurrentSpalla().getCarichi();
        if (currentCarico == null & carichi.size() != 0) {
            currentCarico = (Carico) carichi.get(0);
        }

        return currentCarico;
    }

    public ArrayList getVerticaliIndagate() {
        return getCurrentSpalla().getVerticaliIndagate();
    }

    public void setCurrentCarico(Carico currentCarico) {
        this.currentCarico = currentCarico;
    }

    /**
     *
     * @param fileCorrente
     */
    public void setFileCorrente(File fileCorrente) {
        this.fileCorrente = fileCorrente;
    }

    /**
     *
     */
    public File getFileCorrente() {
        return fileCorrente;
    }

    /**
     * @param nome
     */
    public void addCombinazione(String nome) throws Exception {

        //@todo: some check
        CombinazioniManager.getInstance().addCombinazione(nome,
                getCurrentSpalla().getCombinazioni(),
                currentSpalla.getCarichi().size());
    }

    /**
     * @param id_carico
     */
    public void deleteCarico(Carico c) {
        ArrayList list = (ArrayList) getCurrentSpalla().getCarichi();
        list.remove(c);
        SpallaManager m = SpallaManager.getInstance();
        if (list.size() != 0) {
            m.setCurrentCarico((Carico) list.get(0));
        } else {
            m.setCurrentCarico(null);
        }

        /*      ArrayList list = (ArrayList) getCurrentSpalla().getCarichi();
    Carico carico = (Carico) list.remove(id_carico);
    CombinazioniManager man = CombinazioniManager.getInstance();
    man.deleteCarico(getCurrentSpalla().getCombinazioni(), id_carico);
    //se sto eliminando il carico corrente
    if (currentCarico == carico) {
    //se non ce ne sono più
    if (list.size() == 0) {
    currentCarico = null;
    }
    //se ci sono altri carichi prima di questo lo metto all'inizio
    if (list.size() > 0) {
    currentCarico = (Carico) list.get(0);
    }
    }
         */
    }

    /**
     * @param id_combo
     */
    public void deleteCombinazione(Combinazione c) {
        getCurrentSpalla().getCombinazioni().remove(c);
        currentCombinazione
                = -1;

    }

    /**
     *
     */
    public void addPalo() {
        PaloManager man = PaloManager.getInstance();
        man.addPalo(getCurrentSpalla().getPalificata());
    }

    /**
     * @param id_combo
     */
    public void deletePalo(int id_combo) {
        getCurrentSpalla().getPalificata().remove(id_combo);

    }

    public Combinazione getCurrentCombinazione() {
        ArrayList lista = getCurrentSpalla().getCombinazioni();
        if (currentCombinazione != -1 && lista.size() > currentCombinazione) {
            return (Combinazione) lista.get(currentCombinazione);
        }

        return null;
    }

    public void setCurrentCombinazione(int currentCombinazione) {
        this.currentCombinazione = currentCombinazione;
    }

    /**
     * @return
     */
    public int getCurrentIdCombo() {
        /*Combinazione combo = getCurrentCombinazione();
        ArrayList list = getCurrentSpalla().getCombinazioni();
        int i = list.indexOf( combo );
        //if ( i == -1 ) return 0;
        return i;*/
        return currentCombinazione;
    }

    public void setStratiTerrenoVerticaleCorrente(ArrayList strati) {
        getCurrentVerticale().setStrati(strati);
    }

    public void creaPalificataInAutomatico(double ix, double iy, int nx, int ny, double diametro) {

        Spalla spalla = getCurrentSpalla();
        double alfa = spalla.getAlfa() * Math.PI / 180;

        ArrayList palificata = new ArrayList();
        double lx = ix * (nx - 1);
        double ly = iy * (ny - 1);
        double x0 = -lx / 2;
        double y0 = -ly / 2;

        PaloManager man = PaloManager.getInstance();
        double x = x0;
        double y = y0;
        for (int i = 0; i
                < ny; i++) {
            for (int j = 0; j
                    < nx; j++) {
                Palo p = new Palo(x, y, diametro);
                man.addPalo(palificata, p);
                x
                        += ix;
            }

            x = x0;
            y
                    += iy;
        }

        if (alfa != 0) {
            for (int i = 0; i
                    < palificata.size(); ++i) {
                Palo p = (Palo) palificata.get(i);
                double yp = p.getY();
                double xp = p.getX();
                p.setY(yp + xp * Math.tan(alfa));
            }

        }

        spalla.setPalificata(palificata);
    }

}
