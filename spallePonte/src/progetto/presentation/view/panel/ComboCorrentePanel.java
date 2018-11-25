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
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.openide.util.Exceptions;
import progetto.model.bean.Combinazione;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.commands.SetComboCorrenteCommand;
import progetto.presentation.view.components.AbstractInputPanel;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ComboCorrentePanel extends AbstractInputPanel {

    DefaultListModel listModel;
    JList list;
    private static ComboCorrentePanel panel;

//	private AbstractComboBox comboCarico;
    /**
     *  
     */
    private ComboCorrentePanel() {
        super();
    }

    public synchronized static ComboCorrentePanel getInstance() {
        if (panel == null) {
            panel = new ComboCorrentePanel();
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
            ArrayList listaCombo = del.loadCombinazioniFromModel();
            Combinazione combinazione = del.loadComboCorrente();
            /*         ActionListener listener = comboCarico.getActionListeners()[0];
            comboCarico.removeActionListener(listener);
            comboCarico.setModel(new DefaultComboBoxModel(listaCombo.toArray()));
            comboCarico.setValue(combinazione);
            comboCarico.addActionListener(listener);
             */
            ListSelectionListener listListner = list.getListSelectionListeners()[0];
            list.removeListSelectionListener(listListner);
            listModel.removeAllElements();
            for (int i = 0; i < listaCombo.size(); ++i) {
                listModel.addElement(listaCombo.get(i));
            }
            list.setModel(listModel);
            list.setSelectedValue(combinazione, true);
            list.addListSelectionListener(listListner);


        } catch (Exception ex) {
            ListSelectionListener listListner = list.getListSelectionListeners()[0];
            list.removeListSelectionListener(listListner);
            listModel.removeAllElements();
            list.setModel(listModel);
            list.addListSelectionListener(listListner);
            ex.printStackTrace();
        }
    }

    /*    public AbstractComboBox getComboCarico() {
    return comboCarico;
    }
     */
    /*
     * (non-Javadoc)
     * 
     * @see progetto.presentation.view.components.AbstractInputPanel#init()
     */
    protected void init() {
        //	comboCarico = new DefaultComboBox("setComboCorrente");
        //	setXLabel(80);
        //	addInputField(comboCarico, 0, 0, "comboCorrente", "combo corrente");


        setBorder(new TitledBorder("Elenco combinazioni"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setPreferredSize(new Dimension(300, 400));
        list.setMinimumSize(new Dimension(30, 30));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setAutoscrolls(true);
        JScrollPane jc = new JScrollPane(list);
        jc.setSize(30, 30);

        c.weightx = 1;
        c.weighty = 1;
        c.insets.top = 0;
        c.insets.left = 0;
        c.insets.right = 0;
        c.insets.bottom = 0;
//        c.ipadx = 500;
//        c.gridy = 500;

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
//        c.anchor = GridBagConstraints.CENTER;
        add(jc, c);


        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    new SetComboCorrenteCommand().execute(null);
                    new SetComboCorrenteCommand().forward(null);
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });



    }

    public JList getList() {
        return list;
    }
}
