/*
 * Created on 28-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;
import javax.swing.JPanel;
import progetto.model.util.MetodoK_Rankine;
import progetto.model.util.Metodo_MononobeOkabe;
import progetto.model.util.MetodiCalcoloSpintaTerreno;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.util.MetodoKriposo;
import progetto.presentation.util.MyBeanUtils;
import progetto.presentation.view.components.AbstractInputPanel;
import progetto.presentation.view.components.DefaultCheckBox;
import progetto.presentation.view.components.DefaultComboBox;
import progetto.presentation.view.components.DefaultDoubleField;
import progetto.presentation.view.components.DefaultTextField;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SpallaInputDataPanel extends AbstractInputPanel {

    private static SpallaInputDataPanel panel;

    DefaultComboBox cmbK;
    DefaultCheckBox chkKauto;
    DefaultDoubleField dbf_Kstatic;
    DefaultDoubleField dbf_Kdinamic;
    DefaultDoubleField dbf_Kdinamic_component;
    DefaultDoubleField dbf_Beta_m;
    DefaultDoubleField dbf_sAg;
    DefaultDoubleField dbf_Delta;

    DefaultDoubleField dbf_Fi;
    DefaultCheckBox chkKSismicauto;
    DefaultCheckBox chkNoMuroAlaSx;
    DefaultCheckBox chkNoMuroAlaDx;
    DefaultComboBox cmbKstatic_dinamic;
    DefaultComboBox cmbKdinamic_coomponent;

    public static synchronized SpallaInputDataPanel getInstance() {
        if (panel == null) {
            panel = new SpallaInputDataPanel();
        }
        return panel;
    }

    protected void init() {

        //inizializza Kstatic
        dbf_Kstatic = new DefaultDoubleField();
        dbf_Kdinamic = new DefaultDoubleField();
        dbf_Kdinamic_component = new DefaultDoubleField();
        dbf_Beta_m = new DefaultDoubleField();
        dbf_Beta_m.setEnabled(false);

        dbf_sAg = new DefaultDoubleField();
        dbf_Delta = new DefaultDoubleField();
        dbf_Delta.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {

            }

            public void keyReleased(KeyEvent e) {
                if (chkKSismicauto.isSelected()) {
                    aggiornaKdinamic();
                }
                if (chkKauto.isSelected()) {
                    aggiornaKstatico();
                }
            }

        });
        //casella angolo di attrito interno fi
        dbf_Fi = new DefaultDoubleField();
        dbf_Fi.setEnabled(false);
        dbf_Fi.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {

            }

            public void keyReleased(KeyEvent e) {
                if (chkKSismicauto.isSelected()) {
                    aggiornaKdinamic();
                }
                if (chkKauto.isSelected()) {
                    aggiornaKstatico();
                }
            }

        });

        //combo metodi di calcolo
        cmbK = new DefaultComboBox("ciao");
        cmbK.addItem(new MetodoKriposo());
        cmbK.addItem(new MetodoK_Rankine());
        cmbK.setSelectedIndex(0);
        cmbK.setEnabled(false);
        cmbK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aggiornaKstatico();
            }
        });

        //combo metodi di calcolo
        cmbKstatic_dinamic = new DefaultComboBox("ciao");
        cmbKstatic_dinamic.addItem(new Metodo_MononobeOkabe());
        cmbKstatic_dinamic.setSelectedIndex(0);
        cmbKstatic_dinamic.setEnabled(false);
        cmbKstatic_dinamic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chkKSismicauto.isSelected()) {
                    aggiornaKdinamic();
                }
            }

        });

        //combo metodi di calcolo
        cmbKdinamic_coomponent = new DefaultComboBox("ciao");
        cmbKdinamic_coomponent.addItem(new MetodoKriposo());
        cmbKdinamic_coomponent.addItem(new MetodoK_Rankine());
        cmbKdinamic_coomponent.setSelectedIndex(0);
        cmbKdinamic_coomponent.setEnabled(false);
        cmbKdinamic_coomponent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chkKSismicauto.isSelected()) {
                    aggiornaKdinamic();
                }
            }

        });

        chkKSismicauto = new DefaultCheckBox();
        chkKSismicauto.setSelected(false);

        chkKSismicauto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cmbKdinamic_coomponent.setEnabled(chkKSismicauto.isSelected());
                cmbKstatic_dinamic.setEnabled(chkKSismicauto.isSelected());
                dbf_Kdinamic.setEnabled(!chkKSismicauto.isSelected());
                dbf_Kdinamic_component.setEnabled(!chkKSismicauto.isSelected());
                dbf_Fi.setEnabled(chkKauto.isSelected() || chkKSismicauto.isSelected());
                dbf_Beta_m.setEnabled(chkKSismicauto.isSelected());

                if (chkKSismicauto.isSelected()) {
                    aggiornaKdinamic();
                }

            }
        });

        dbf_Beta_m.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (chkKSismicauto.isSelected()) {
                    aggiornaKdinamic();
                };
            }
        });

        dbf_Beta_m.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (chkKSismicauto.isSelected()) {
                    aggiornaKdinamic();
                }
            }
        });

        dbf_Delta.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (chkKSismicauto.isSelected()) {
                    aggiornaKdinamic();
                }
                if (chkKauto.isSelected()) {
                    aggiornaKstatico();
                }
            }
        });

        chkKauto = new DefaultCheckBox();
        chkKauto.setSelected(false);
        chkKauto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cmbK.setEnabled(chkKauto.isSelected());
                dbf_Kstatic.setEnabled(!chkKauto.isSelected());
                dbf_Fi.setEnabled(chkKauto.isSelected());

            }
        });

        chkNoMuroAlaSx = new DefaultCheckBox();
        chkNoMuroAlaDx = new DefaultCheckBox();
        
        JPanel pNome = addInputPanel("Descrizione", 0, 0);
        addInputField(pNome, new DefaultTextField(), 0, 0, "nome", "Nome dell'opera");

        JPanel pPesi = addInputPanel("Pesi Specifici", 1, 0);
        addInputField(pPesi, new DefaultDoubleField(), 0, 0, "gammaCLS", "Peso specifico cls(kN/mc)");
        addInputField(pPesi, new DefaultDoubleField(), 1, 0, "gammaTerreno", "Peso specifico terreno(kN/mc)");

        JPanel pTerrenoCLS = addInputPanel("Terreno", 2, 0);
        addInputField(pTerrenoCLS, new DefaultDoubleField(), 0, 0, "h_terreno", "Altezza terreno di monte(m)");
        addInputField(pTerrenoCLS, new DefaultDoubleField(), 1, 0, "h_terrenoValle", "Altezza terreno di valle(m)");
        addInputField(pTerrenoCLS, dbf_Kstatic, 2, 0, "k_static", "Coeff. di spinta K");
        addInputField(pTerrenoCLS, dbf_Fi, 3, 0, "fi_Terreno", "Angolo di attrito terreno");
        addInputField(pTerrenoCLS, chkKauto, 4, 0, "k_static_auto", "Calcolo autom.");
        addInputField(pTerrenoCLS, cmbK, 5, 0, "metodo_Kstatic", "Metodo");
        addInputField(pTerrenoCLS, new DefaultDoubleField(), 6, 0, "q_carico", "Sovraccarico(kN/mq)");
        addInputField(pTerrenoCLS, dbf_Delta, 7, 0, "deltaTerreno", "Delta");
        addInputField(pTerrenoCLS, new DefaultCheckBox(), 8, 0, "spintaVerticale", "Spinta verticale terreno");

        JPanel pSisma = addInputPanel("Azioni sismiche", 3, 0);
        addInputField(pSisma, dbf_sAg, 1, 0, "agS", "accelereazione S*ag");
        addInputField(pSisma, dbf_Kdinamic, 2, 0, "k_static_dinamic", "K (statico+dinam.)");
        addInputField(pSisma, dbf_Kdinamic_component, 3, 0, "k_dinamic", "kh (dinamico)");
        addInputField(pSisma, chkKSismicauto, 4, 0, "k_dinamic_auto", "Calcolo automatico di kh (dinamico)");
        addInputField(pSisma, cmbKstatic_dinamic, 5, 0, "metodo_Kdinamic", "Metodo di calcolo kh (dinamico)");
        addInputField(pSisma, cmbKdinamic_coomponent, 6, 0, "metodo_Kdinamic_coomponent", "Metodo di calcolo khs (parte statica)");
        addInputField(pSisma, dbf_Beta_m, 7, 0, "beta_m", "beta m sismico");

        JPanel pFondazioni = addInputPanel("Fondazione", 4, 0);
        addInputField(pFondazioni, new DefaultDoubleField(), 0, 0, "bxFonda", "Larghezza Bx fondazione(m)");
        addInputField(pFondazioni, new DefaultDoubleField(), 1, 0, "byFonda", "Larghezza By fondazione(m)");
        addInputField(pFondazioni, new DefaultDoubleField(), 2, 0, "spFonda", "Spessore fondazione(m)");
        addInputField(pFondazioni, new DefaultDoubleField(), 3, 0, "alfa", "Incl. asse impalcato(deg)");

        JPanel pElevazioni = addInputPanel("Elevazioni", 5, 0);
        addInputField(pElevazioni, new DefaultDoubleField(), 4, 0, "xgElevazione", "Coordinata x baricentro(m)");
        addInputField(pElevazioni, new DefaultDoubleField(), 5, 0, "ygElevazione", "Coordinata y baricentro(m)");
        addInputField(pElevazioni, new DefaultDoubleField(), 1, 0, "bxElevazione", "Larghezza Bx elevazione(m)");
        addInputField(pElevazioni, new DefaultDoubleField(), 2, 0, "byElevazione", "Larghezza By elevazione(m)");
        addInputField(pElevazioni, new DefaultDoubleField(), 3, 0, "hsElevazione", "Hs altezza elevazione(m)");

        JPanel pParaghiaia = addInputPanel("Paraghiaia", 6, 0);
        addInputField(pParaghiaia, new DefaultDoubleField(), 1, 0, "HPara", "Altezza paraghiaia(m)");
        addInputField(pParaghiaia, new DefaultDoubleField(), 2, 0, "spPara", "Spessore paraghiaia(m)");
        addInputField(pParaghiaia, new DefaultDoubleField(), 3, 0, "dyPara", "dy (m)");

        JPanel pMuri = addInputPanel("Muri d'ala", 7, 0);
        addInputField(pMuri, new DefaultDoubleField(), 1, 0, "LMuri", "Lunghezza muri(m)");
        addInputField(pMuri, new DefaultDoubleField(), 2, 0, "spMuri", "Spessore muri(m)");
        addInputField(pMuri,  chkNoMuroAlaSx, 3, 0, "noMuroAlaSx", "Rimuovi muro d'ala sinistro");
        addInputField(pMuri,  chkNoMuroAlaDx, 4, 0, "noMuroAlaDx", "Rimuovi muro d'ala destro");
      
    }

    private void aggiornaKdinamic() {
        try {
            if (cmbKstatic_dinamic.getSelectedItem().getClass() == Metodo_MononobeOkabe.class) {
                Metodo_MononobeOkabe mo = (Metodo_MononobeOkabe) cmbKstatic_dinamic.getSelectedItem();
                double betam = Double.parseDouble(dbf_Beta_m.getText());
                double sAg = Double.parseDouble(dbf_sAg.getText());
                mo.setsAg(sAg);
                mo.setBeta_m(betam);
            }
            double delta = Double.parseDouble(dbf_Delta.getText());
            Double fi = Double.parseDouble(dbf_Fi.getText());
            MetodiCalcoloSpintaTerreno mcS = (MetodiCalcoloSpintaTerreno) cmbKdinamic_coomponent.getSelectedItem();
            MetodiCalcoloSpintaTerreno mcD = (MetodiCalcoloSpintaTerreno) cmbKstatic_dinamic.getSelectedItem();
            double ks = mcS.getK(Math.toRadians(fi), Math.toRadians(delta));
            double kd = mcD.getK(Math.toRadians(fi), Math.toRadians(delta));

            dbf_Kdinamic.setText(String.format(Locale.US, "%1$.3f", kd));
            dbf_Kdinamic_component.setText(String.format(Locale.US, "%1$.3f", kd - ks));
        } catch (Exception ex) {
        }
    }

    private void aggiornaKstatico() {
        try {
            Double fi = Double.parseDouble(dbf_Fi.getText());
            MetodiCalcoloSpintaTerreno mc = (MetodiCalcoloSpintaTerreno) cmbK.getSelectedItem();
            double ks = mc.getK(Math.toRadians(fi));
            dbf_Kstatic.setText(String.format(Locale.US, "%1$.3f", ks));
        } catch (Exception ex) {
        }

    }

    /**
     *
     */
    private SpallaInputDataPanel() {
        super();
    }

    /* (non-Javadoc)
	 * @see progetto.presentation.view.util.ViewComponent#refreshModel()
     */
    public void refreshView() {
        SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
        try {
            MyBeanUtils.setProperties(del.loadSpallaCorrenteFromModel(), inputs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
