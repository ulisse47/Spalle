/*
 * Created on 28-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import progetto.model.bean.Berezantzev;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.Verticale;
import progetto.model.util.Calcoli;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.view.components.AbstractInputPanel;
import progetto.presentation.view.components.FormattedDoubleField;
import progetto.presentation.view.util.InputComponent;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BerezantzevOutputDataPanel extends AbstractInputPanel {

    private static BerezantzevOutputDataPanel panel;
    JTextField[] txtNq;
    JTextField[] txtFi;
    JTextField[] txtL_D;
    JLabel[] lblNq;
    JLabel[] lblFi;
    JLabel[] lblL_D;

    public JTextField[] getTxtNq() {
        return txtNq;
    }

    public static synchronized BerezantzevOutputDataPanel getInstance() {
        //   if (panel == null) {
        panel = new BerezantzevOutputDataPanel();
        // }
        return panel;
    }

    protected void init() {

        SpallaManager manager = SpallaManager.getInstance();
        Spalla sp = manager.getCurrentSpalla();
        ArrayList vert = sp.getVerticaliIndagate();
        int ns = vert.size();

        setLayout(new GridLayout(ns, 0));

        txtNq = new JTextField[ns];
        txtFi = new JTextField[ns];
        txtL_D = new JTextField[ns];
        lblNq = new JLabel[ns];
        lblFi = new JLabel[ns];
        lblL_D = new JLabel[ns];

        //setXLabel(250);
        int w = 30;
        int h = 10;

           for (int i = 0; i < ns; ++i) {
            Verticale v = (Verticale) vert.get(i);
            String s1 = "θ (medio base Zbase)";
            String s2 = "L/D";
            String s3 = "Nq";
            JPanel Pvert = new JPanel(new GridBagLayout());
            TitledBorder titledBorder = new TitledBorder(
                    BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151)), v.getName());
            Pvert.setBorder(titledBorder);
            setBorder(new TitledBorder("Parametri verticali indagate"));
            GridBagConstraints c = new GridBagConstraints();

            txtFi[i] = new JTextField();
            txtFi[i].setEnabled(true);
            txtFi[i].setPreferredSize(new Dimension(w, h));
            lblFi[i] = new JLabel(s1);
            lblFi[i].setPreferredSize(new Dimension(w, h));
            txtL_D[i] = new JTextField();
            txtL_D[i].setEnabled(true);
            txtL_D[i].setPreferredSize(new Dimension(w, h));
            lblL_D[i] = new JLabel(s2);
            lblL_D[i].setPreferredSize(new Dimension(w, h));
            txtNq[i] = new JTextField();
            txtNq[i].setEnabled(true);
            txtNq[i].setPreferredSize(new Dimension(w, h));
            lblNq[i] = new JLabel(s3);
            //            lblNq[i].setSize(w, h);
            lblNq[i].setPreferredSize(new Dimension(w, h));


            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.WEST;
            c.insets.left = 5;
            c.insets.top = 5;
            c.ipadx = 50;
            c.ipady = 10;
            c.gridx = 0;
            c.gridy = 0;
            Pvert.add(lblFi[i], c);
            c.gridx = 0;
            c.gridy = 1;
            Pvert.add(lblL_D[i], c);
            c.gridx = 0;
            c.gridy = 2;
            Pvert.add(lblNq[i], c);

            c.insets.right = 5;
            c.anchor = GridBagConstraints.EAST;
            c.ipadx = 30;
            c.ipady = 8;
            c.gridx = 1;
            c.gridy = 0;
            Pvert.add(txtFi[i], c);
            c.gridx = 1;
            c.gridy = 1;
            Pvert.add(txtL_D[i], c);
            c.gridx = 1;
            c.gridy = 2;
            Pvert.add(txtNq[i], c);
            add(Pvert);

        }

    }

    BerezantzevOutputDataPanel() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see progetto.presentation.view.util.ViewComponent#refreshModel()
     */
    public void refreshView() {
        SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
        SpallaManager manager = SpallaManager.getInstance();
        Spalla sp = manager.getCurrentSpalla();
        double L = sp.getLpalo();
        double Dpalo = sp.getDpalo();
        double L_D = L / Dpalo;
        ArrayList vert = sp.getVerticaliIndagate();
        int ns = vert.size();

        try {

            for (int i = 0; i <
                    ns; ++i) {
                Verticale v = (Verticale) vert.get(i);
                double fi = del.calcolaFiMedioTestaPalo(v);
                double Nq = new Berezantzev().getNq(fi, L_D);
                txtNq[i].setText(Double.toString((Math.ceil(Nq * 100)) / 100));
                txtFi[i].setText(Double.toString((Math.ceil(fi * 100)) / 100));
                txtL_D[i].setText(Double.toString((Math.ceil(L_D * 100)) / 100));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void ricalcola() {
        SpallaManager manager = SpallaManager.getInstance();
        Spalla sp = manager.getCurrentSpalla();
        ArrayList vert = sp.getVerticaliIndagate();
        int ns = vert.size();

        try {
            for (int i = 0; i < ns; ++i) {
                double L_D = Double.parseDouble(txtL_D[i].getText());
                double fi = Double.parseDouble(txtFi[i].getText());
                double Nq = new Berezantzev().getNq(fi, L_D);
                txtNq[i].setText(Double.toString((Math.ceil(Nq * 100)) / 100));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public double[] getNq() {

        try {
            SpallaManager manager = SpallaManager.getInstance();
            Spalla sp = manager.getCurrentSpalla();
            ArrayList vert = sp.getVerticaliIndagate();
            int ns = vert.size();
            double[] Nq = new double[ns];
            for (int i = 0; i < ns; ++i) {
                Nq[i] = Double.parseDouble(txtNq[i].getText());
            }

            return Nq;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
