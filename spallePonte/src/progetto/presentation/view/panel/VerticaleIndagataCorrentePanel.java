/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.openide.util.Exceptions;
import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.Verticale;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.commands.SetVerticaleCorrenteCommand;
import progetto.presentation.view.components.AbstractComboBox;
import progetto.presentation.view.components.AbstractInputPanel;
import progetto.presentation.view.components.AbstractTextField;
import progetto.presentation.view.components.DefaultTextField;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VerticaleIndagataCorrentePanel extends AbstractInputPanel {

    private static VerticaleIndagataCorrentePanel panel;
    private AbstractComboBox comboVerticale;
    private AbstractTextField txtNq;
    private AbstractTextField txtNc;
    private AbstractTextField txtLpalo;
    private AbstractTextField txtZtestaPalo;
    private AbstractTextField txtDPalo;
    private AbstractTextField txtZfalda;
    private AbstractTextField txtCum;
    private DefaultListModel listModel;
    private JList list;

    /**
     *  
     */
    private VerticaleIndagataCorrentePanel() {
        super();

    }

    public synchronized static VerticaleIndagataCorrentePanel getInstance() {
        if (panel == null) {
            panel = new VerticaleIndagataCorrentePanel();
        }
        return panel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progetto.presentation.view.util.ViewComponent#refreshView()
     */
    public void refreshView() {


        try {

            SpallaManager manager = SpallaManager.getInstance();
            Spalla sp = manager.getCurrentSpalla();
            SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
            ArrayList verticali = manager.getVerticaliIndagate();
            Verticale curV = manager.getCurrentVerticale();

            //Lpalo Zpalo
            double Lpalo = sp.getLpalo();
            double Zpalo = sp.getZpalo();
            double Dpalo = sp.getDpalo();
            double Zfalda = sp.getZfalda();

            txtLpalo.setText(Double.toString(Lpalo));
            txtZtestaPalo.setText(Double.toString(Zpalo));
            txtDPalo.setText(Double.toString(Dpalo));
            txtZfalda.setText(Double.toString(Zfalda));

            if (curV != null) {


                ListSelectionListener listListner = list.getListSelectionListeners()[0];
                list.removeListSelectionListener(listListner);
                listModel.removeAllElements();
                for (int i = 0; i < verticali.size(); ++i) {
                    listModel.addElement(verticali.get(i));
                }
                list.setModel(listModel);
                list.setSelectedValue(curV, true);
                list.addListSelectionListener(listListner);


                //Nc ed Nq
                double Nq = curV.getNq();
                double Nc = curV.getNc();
                txtNq.setText(Double.toString(Nq));
                txtNc.setText(Double.toString(Nc));

                //   InputComponent comp;
                double CuM = (Math.ceil(del.calcolaCuMedioTestaPalo(curV) * 100)) / 100;
                //   comp = (InputComponent) outputs.get("CuM");
                // comp.setValue(new Double(CuM));
                try {
                    txtCum.setValue(new Double(CuM));

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "problemi nel calcolo di Cu medio base palo",
                            "Informazione", JOptionPane.INFORMATION_MESSAGE);
                }



            } else {

                ListSelectionListener listListner = list.getListSelectionListeners()[0];
                list.removeListSelectionListener(listListner);
                listModel.removeAllElements();
                list.setModel(listModel);
                list.addListSelectionListener(listListner);


                /*   ActionListener listener = comboVerticale.getActionListeners()[0];
                comboVerticale.removeActionListener(listener);
                comboVerticale.removeAllItems();
                comboVerticale.addActionListener(listener);
                 */
                txtNq.setText("");
                txtNc.setText("");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AbstractTextField getTxtZfalda() {
        return txtZfalda;
    }

    public AbstractComboBox getComboCarico() {
        return comboVerticale;
    }

    public AbstractTextField getTxtNc() {
        return txtNc;
    }

    public AbstractTextField getTxtNq() {
        return txtNq;
    }

    public AbstractTextField getTxtLpalo() {
        return txtLpalo;
    }

    public AbstractTextField getTxtZtestaPalo() {
        return txtZtestaPalo;
    }

    public AbstractTextField getTxtDPalo() {
        return txtDPalo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progetto.presentation.view.components.AbstractInputPanel#init()
     */
    protected void init() {

        setBorder(new TitledBorder("Verticali indagate"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int alt = 8;
        int largh = 30;
        txtNq = new DefaultTextField();
        txtNq.setPreferredSize(new Dimension(largh, alt));

        txtNc = new DefaultTextField();
        txtNc.setPreferredSize(new Dimension(largh, alt));

        txtCum = new DefaultTextField();
        txtCum.setPreferredSize(new Dimension(largh, alt));
        txtCum.setEditable(false);

        txtDPalo = new DefaultTextField();
        txtDPalo.setPreferredSize(new Dimension(largh, alt));

        txtLpalo = new DefaultTextField();
        txtLpalo.setPreferredSize(new Dimension(largh, alt));

        txtZfalda = new DefaultTextField();
        txtZfalda.setPreferredSize(new Dimension(largh, alt));

        txtZtestaPalo = new DefaultTextField();
        txtZtestaPalo.setPreferredSize(new Dimension(largh, alt));

        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setPreferredSize(new Dimension(100, 500));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setAutoscrolls(true);

        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    new SetVerticaleCorrenteCommand().execute(null);
                    new SetVerticaleCorrenteCommand().forward(null);
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });

        list.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                SpallaManager sp =
                        SpallaManager.getInstance();
                Verticale vert = sp.getCurrentVerticale();

                try {
                    if (e.getClickCount() == 2) {

                        //componente businness
                        Object returnValue = JOptionPane.showInputDialog(null,
                                "nome della verticale", "rinomina verticale indagata",
                                JOptionPane.QUESTION_MESSAGE, null, null, vert.getName());
                        if (returnValue != null) {
                            String nome = (String) returnValue;
                            sp.setNameCurrentVerticale(nome);
                            refreshView();
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error processing " + this.getClass().getName() + ex.toString());
                }
            }

            public void mousePressed(MouseEvent e) {
            //     throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseReleased(MouseEvent e) {
            //   throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseEntered(MouseEvent e) {
            //   throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseExited(MouseEvent e) {
            //      throw new UnsupportedOperationException("Not supported yet.");
            }
        });


        //LISTA
        c.weightx = 1;
        c.weighty = 1;
        c.ipadx = 5;
        c.ipady = 5;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 4;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        add(new JScrollPane(list), c);

        //LABEL
        alt = 8;
        largh = 80;
        c.ipadx = 20;
        c.ipady = 8;
        c.insets.left = 5;
        c.insets.top = 0;
        c.weightx = 0;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;
        c.gridy = 0;
        JLabel njl = new JLabel("Nq");
        njl.setPreferredSize(new Dimension(largh, alt));
        add(njl, c);
        c.gridx = 1;
        c.gridy = 1;
        njl = new JLabel("NC");
        njl.setPreferredSize(new Dimension(largh, alt));
        add(njl, c);
        c.gridx = 1;
        c.gridy = 2;
        njl = new JLabel("Cu medio testa palo");
        njl.setPreferredSize(new Dimension(largh, alt));
        add(njl, c);
        c.gridx = 1;
        c.gridy = 3;
        add(new JLabel(""), c);

        //TXT
        c.ipadx = 20;
        c.ipady = 8;
        c.insets.left = 5;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 2;
        c.gridy = 0;
        add(txtNq, c);
        c.gridx = 2;
        c.gridy = 1;
        add(txtNc, c);
        c.gridx = 2;
        c.gridy = 2;
        add(txtCum, c);

        //PALO LABEL
        c.ipadx = 20;
        c.ipady = 8;
        c.insets.left = 15;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 3;
        c.gridy = 0;
        njl = new JLabel("Lpalo (m)");
        njl.setPreferredSize(new Dimension(largh, alt));
        add(njl, c);
        c.gridx = 3;
        c.gridy = 1;
        njl = new JLabel("Quota tesat palo (m)");
        njl.setPreferredSize(new Dimension(largh, alt));
        add(njl, c);
        c.gridx = 3;
        c.gridy = 2;
        njl = new JLabel("Diametro palo (m)");
        njl.setPreferredSize(new Dimension(largh, alt));
        add(njl, c);
        c.gridx = 3;
        c.gridy = 3;
        njl = new JLabel("Quota falda (m)");
        njl.setPreferredSize(new Dimension(largh, alt));
        add(njl, c);


        //testi palo
        c.ipadx = 20;
        c.ipady = 8;
        c.insets.left = 5;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 4;
        c.gridy = 0;
        add(txtLpalo, c);
        c.gridx = 4;
        c.gridy = 1;
        add(txtZtestaPalo, c);
        c.gridx = 4;
        c.gridy = 2;
        add(txtDPalo, c);
        c.gridx = 4;
        c.gridy = 3;
        add(txtZfalda, c);

    }

    public JList getList() {
        return list;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }
}
