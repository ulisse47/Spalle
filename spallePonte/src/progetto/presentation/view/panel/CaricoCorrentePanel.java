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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
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
import progetto.model.bean.Carico;
import progetto.model.bean.SpallaManager;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.commands.SetCaricoCorrenteCommand;
import progetto.presentation.view.components.AbstractCheckBox;
import progetto.presentation.view.components.AbstractComboBox;
import progetto.presentation.view.components.AbstractInputPanel;
import progetto.presentation.view.components.DefaultButton;
import progetto.presentation.view.components.DefaultCheckBox;
import progetto.presentation.view.table.TableCarichi;
import progetto.presentation.view.table.TableCombinazioni;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CaricoCorrentePanel extends AbstractInputPanel {

//	private JScrollPane tableAppoggiScrollPane;
    private static CaricoCorrentePanel panel;
    private AbstractComboBox comboCarico;
    ActionListener listn;
//    private JList listCarichi;
    private AbstractCheckBox ckCaricoAgenteSpalla;

    public DefaultListModel getListModel() {
        return listModel;
    }

    public void setListModel(DefaultListModel listModel) {
        this.listModel = listModel;
    }

    public JList getList() {
        return list;
    }

    public void setList(JList list) {
        this.list = list;
    }
    private AbstractCheckBox ckCaricoAgenteAppoggi;
    private AbstractCheckBox ckCaricoPermanente;
    private AbstractCheckBox ckCaricoAtrito;
    private AbstractButton btUp;
    private AbstractButton btDown;
    SpalleBusinessDelegate bdelegate;
    JList list;
    DefaultListModel listModel;

    /**
     *  
     */
    private CaricoCorrentePanel() {
        super();
    }

    public synchronized static CaricoCorrentePanel getInstance() {
        if (panel == null) {
            panel = new CaricoCorrentePanel();
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

            SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
            ArrayList listaCarichi = del.loadCarichiFromModel();
            Carico carico = del.loadCaricoCorrente();

            if (carico != null) {

                //               ActionListener listener = comboCarico.getActionListeners()[0];
                //              comboCarico.removeActionListener(listener);
                /*comboCarico.setModel(new DefaultComboBoxModel(listaCarichi.toArray()));
                comboCarico.setValue(carico);
                comboCarico.addActionListener(listener);
                 */
                ListSelectionListener listListner = list.getListSelectionListeners()[0];
                list.removeListSelectionListener(listListner);
                listModel.removeAllElements();
                for (int i = 0; i < listaCarichi.size(); ++i) {
                    listModel.addElement(listaCarichi.get(i));
                }
                list.setModel(listModel);
                list.setSelectedValue(carico, true);
                list.addListSelectionListener(listListner);

                ckCaricoAgenteAppoggi.setSelected(carico.isAgenteSuAppoggi());
                ckCaricoAgenteSpalla.setSelected(carico.isAgenteSuElevazioni());
                ckCaricoPermanente.setSelected(carico.isPermanente());
                ckCaricoAtrito.setSelected(carico.isAtrito());
            } else {
                /* ActionListener listener = comboCarico.getActionListeners()[0];
                comboCarico.removeActionListener(listener);
                comboCarico.setModel(new DefaultComboBoxModel(listaCarichi.toArray()));
                comboCarico.removeAllItems();
                comboCarico.addActionListener(listener);
                 */
                ListSelectionListener listListner = list.getListSelectionListeners()[0];
                list.removeListSelectionListener(listListner);
                listModel.removeAllElements();
                list.setModel(listModel);
                list.addListSelectionListener(listListner);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public AbstractComboBox getComboCarico() {
        return comboCarico;
    }

    public AbstractCheckBox getCkAgenteSpalla() {
        return ckCaricoAgenteSpalla;
    }

    public AbstractCheckBox getCkAgenteAppoggi() {
        return ckCaricoAgenteAppoggi;
    }

    public AbstractCheckBox getCkPermanente() {
        return ckCaricoPermanente;
    }

    public AbstractCheckBox getCkAtrito() {
        return ckCaricoAtrito;
    }


    /*
     * (non-Javadoc)
     * 
     * @see progetto.presentation.view.components.AbstractInputPanel#init()
     */
    protected void init() {


//        setBounds(new Rectangle(100, 300));
        setBorder(new TitledBorder("Proprietà carico corrente"));
//        GridBagLayout gbl = new GridBagLayout();
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        bdelegate = SpalleBusinessDelegateImpl.getInstance();
        listn = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int ns = list.getSelectedIndex();
                if (ns == -1) {
                    return;
                }
                if (e.getActionCommand().equals("cmdUp")) {
                    try {
                        bdelegate.salvaInLocale();
                        bdelegate.moveUpCaricoCorrente(ns);
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }

                }
                if (e.getActionCommand().equals("cmdDown")) {
                    try {
                        bdelegate.salvaInLocale();
                        bdelegate.moveDownCaricoCorrente(ns);
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }


                TableCarichi.getInstance().refreshView();
                CaricoCorrentePanel.getInstance().refreshView();
                TableCombinazioni.getInstance().refreshView();


            }
        };

//        comboCarico = new DefaultComboBox("setCaricoCorrente");
        //       comboCarico.setPreferredSize(new DimensionUIResource(150, 20));
        ckCaricoAgenteAppoggi = new DefaultCheckBox("");
        ckCaricoAgenteSpalla = new DefaultCheckBox("");
        ckCaricoPermanente = new DefaultCheckBox("");
        ckCaricoAtrito = new DefaultCheckBox("");

        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setPreferredSize(new Dimension(200, 600));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setAutoscrolls(true);

        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    new SetCaricoCorrenteCommand().execute(null);
                    new SetCaricoCorrenteCommand().forward(null);
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });

        list.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
             SpallaManager sp = SpallaManager.getInstance();

                try {
                    if (e.getClickCount() == 2) {

                        String st = sp.getCurrentCarico().getName();
                        Object returnValue = JOptionPane.showInputDialog(null,
                                "nome del carico", "rinomina carico",
                                JOptionPane.QUESTION_MESSAGE, null, null, st);
                        if (returnValue != null) {
                            String nome = (String) returnValue;
                            sp.setNameCurrentCarico(nome);
                            refreshView();
                            TableCombinazioni.getInstance().refreshView();

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

        btUp = new DefaultButton("", "");
        btUp.setSize(40, 5);
        btUp.setText("su");
        btUp.setActionCommand("cmdUp");
        btUp.addActionListener(listn);

        btDown = new DefaultButton("", "");
        btDown.setSize(40, 5);
        btDown.setText("giù");
        btDown.setActionCommand("cmdDown");
        btDown.addActionListener(listn);

        //LISTA
        c.weightx = 1;
        c.ipadx = 5;
        c.ipady = 5;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 6;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        add(new JScrollPane(list), c);

        //APPOGGI +CK
        c.insets.left = 15;
        c.insets.top = 5;

        c.weightx = 0;
        c.ipadx = 0;
        c.ipady = 5;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;
        c.gridy = 0;
        c.insets.left = 30;
        add(new JLabel("Agisce su appoggi"), c);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        c.insets.left = 15;
        add(ckCaricoAgenteAppoggi, c);

        //PERM +CK
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets.left = 30;
        add(new JLabel("Carico permanente"), c);
        c.gridx = 2;
        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        c.insets.left = 15;
        add(ckCaricoPermanente, c);

        //ATRITO +CK
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        c.insets.left = 30;
        add(new JLabel("Atrito parassita"), c);
        c.gridx = 2;
        c.gridy = 2;
        c.anchor = GridBagConstraints.EAST;
        c.insets.left = 15;
        add(ckCaricoAtrito, c);

        //BOTTONI
        c.insets.left = 15;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 3;
        add(btUp, c);

        c.gridx = 1;
        c.gridy = 4;
        add(btDown, c);


    }
}
