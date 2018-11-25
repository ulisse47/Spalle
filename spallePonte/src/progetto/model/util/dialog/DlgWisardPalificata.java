package progetto.model.util.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import progetto.model.bean.PalificataWizard_Set;
import progetto.model.bean.SpallaManager;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.util.MyTextField;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Andrea Cavalieri
 * @version 1.0
 */
public class DlgWisardPalificata extends JDialog {

    private SpalleBusinessDelegate bDelegate =
            SpalleBusinessDelegateImpl.getInstance();
    private JPanel pannello;
    JTextField ix = new MyTextField();
    JLabel lix = new JLabel();
    JTextField iy = new MyTextField();
    JLabel liy = new JLabel();
    JTextField nx = new MyTextField();
    JLabel lnx = new JLabel();
    JTextField ny = new MyTextField();
    JLabel lny = new JLabel();
    JTextField diametro = new MyTextField();
    JLabel ldiametro = new JLabel();
    
    private JButton jButtonOk = new JButton();
    private JButton jButtonCancel = new JButton();

    public DlgWisardPalificata() throws HeadlessException {

        this.setModal(true);
        pannello = new JPanel();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        this.setSize(355, 200);
        this.setLocation(400, 200);
        this.setResizable(false);
        this.setTitle("Wizard Palificata");
        PalificataWizard_Set pWS = SpallaManager.getInstance().getPalificataWS();

        TitledBorder titledBorderSezione = new TitledBorder(new EtchedBorder(
                EtchedBorder.RAISED, Color.white, Color.gray), "Dati geometrici");

        pannello.setLayout(new GridBagLayout());
        pannello.setBorder(titledBorderSezione);
        pannello.setVisible(true);
        pannello.setBounds(new Rectangle(0, 0, 350, 140));

        addComponent(pannello, "Interasse pali direzione x: ix (m)",
                lix, ix, 0, 0, 80, 21);
        addComponent(pannello, "Interasse pali direzione y: iy (m)",
                liy, iy, 0, 1, 80, 21);
        addComponent(pannello, "Numero pali direzione x: nx",
                lnx, nx, 0, 2, 80, 21);
        addComponent(pannello, "Numero pali direzione y: ny",
                lny, ny, 0, 3, 80, 21);
        addComponent(pannello, "Diametro pali: D (m)",
                ldiametro, diametro, 0, 4, 80, 21);



        jButtonOk.setBounds(new Rectangle(0, 145, 85, 20));
        jButtonCancel.setBounds(new Rectangle(90, 145, 85, 20));
        jButtonOk.setText("OK");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                jButtonOk_actionPerformed(e);
            }
        });
        jButtonCancel.setText("CANCEL");

        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                jButtonCancel_actionPerformed(e);
            }
        });

        this.getContentPane().add(pannello, null);
        this.getContentPane().add(jButtonCancel, null);
        this.getContentPane().add(jButtonOk, null);

        
        ix.setText(Double.toString(pWS.getInterasseX()));
        iy.setText(Double.toString(pWS.getInterasseY()));
        nx.setText(Integer.toString(pWS.getnX()));
        ny.setText(Integer.toString(pWS.getnY()));
        diametro.setText(Double.toString(pWS.getDiametroPali()));

        
        
    }

    private void addComponent(JPanel pan, String title, JLabel lBs1,
            JTextField bs1, int gridx, int gridy, int width, int height) {


        lBs1.setHorizontalAlignment(SwingConstants.LEFT);
        lBs1.setText(title);
        lBs1.setPreferredSize(new Dimension(230, 15));

        bs1.setPreferredSize(new Dimension(width, height));
        bs1.setHorizontalAlignment(SwingConstants.RIGHT);

        pan.add(lBs1, new GridBagConstraints(gridx, gridy, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
                0, 0, 0, 0), 9, 0));
        pan.add(bs1, new GridBagConstraints(gridx + 1, gridy, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
                0, 0, 0, 0), 0, 0));
    }

    void jButtonOk_actionPerformed(ActionEvent e) {
        double d_ix, d_iy, d_diametro;
        int i_nx, i_ny;
                
        try {
            d_ix = Double.parseDouble(ix.getText());
            ix.setBackground(Color.white);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Valore di ix non valido", "Attenzione", JOptionPane.ERROR_MESSAGE, null);
            ix.setBackground(Color.yellow);
            return;
        }

        try {
            d_iy = Double.parseDouble(iy.getText());
            iy.setBackground(Color.white);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Valore di iy non valido", "Attenzione", JOptionPane.ERROR_MESSAGE, null);
            iy.setBackground(Color.yellow);
            return;
        }

        try {
            i_nx = Integer.parseInt(nx.getText());
            nx.setBackground(Color.white);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Valore di nx non valido", "Attenzione", JOptionPane.ERROR_MESSAGE, null);
            nx.setBackground(Color.yellow);
            return;
        }

        try {
            i_ny = Integer.parseInt(ny.getText());
            ny.setBackground(Color.white);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Valore di ny non valido", "Attenzione", JOptionPane.ERROR_MESSAGE, null);
            ny.setBackground(Color.yellow);
            return;
        }

        try {
            d_diametro = Double.parseDouble(diametro.getText());
            diametro.setBackground(Color.white);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Diametro non valido", "Attenzione", JOptionPane.ERROR_MESSAGE, null);
            diametro.setBackground(Color.yellow);
            return;
        }


       SpallaManager.getInstance().setPalificataWS(new PalificataWizard_Set(d_ix,d_iy,i_nx,i_ny,d_diametro));

        try {
            bDelegate.creaPalificataInAutomatico(d_ix, d_iy, i_nx, i_ny, d_diametro);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Operazione non riuscita",
                    "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }

    void jButtonCancel_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}

