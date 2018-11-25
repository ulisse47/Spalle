package progetto.model.util.dialog;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.Verticale;
import progetto.presentation.view.panel.BerezantzevOutputDataPanel;
import progetto.presentation.view.panel.VerticaleIndagataCorrentePanel;
import progetto.presentation.view.table.TableBerenzantzev;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Andrea Cavalieri
 * @version 1.0
 */
public class DlgCalcoloAuotmaticoNq extends JDialog {

    private JButton jButtonOk = new JButton();
    private JButton jButtonCancel = new JButton();
    private JButton jButtonAggiorna = new JButton();
    private JButton jButtonRicalcola = new JButton();
    private JScrollPane scrollPTab;
    private JScrollPane scrollNq;
    JSplitPane mainSplitPane;
    JSplitPane sSplitPane;
    JPanel pBotton;
    private BerezantzevOutputDataPanel berPanel = BerezantzevOutputDataPanel.getInstance();

    public DlgCalcoloAuotmaticoNq() throws HeadlessException {

        this.setModal(true);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {


        setLayout(new BorderLayout());

        mainSplitPane = new JSplitPane();
        mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        sSplitPane = new JSplitPane();
        sSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        pBotton = new JPanel();
        berPanel.refreshView();
        scrollNq = new JScrollPane();
        scrollNq.getViewport().add(berPanel);
        scrollNq.setMinimumSize(new Dimension(50, 100));

        this.setSize(800, 500);
        this.setLocation(400, 200);
        this.setResizable(true);
        this.setTitle("Calcolo automatico Nq (metodo Berezantzev)");

        TableBerenzantzev tab = TableBerenzantzev.getInstance();

        tab.setMinimumSize(new Dimension(500, 400));
        scrollPTab = new JScrollPane();
        scrollPTab.add(tab);
        scrollPTab.getViewport().add(tab);

  //      sSplitPane.setLeftComponent(scrollPTab);
  //      sSplitPane.setRightComponent(scrollNq);

        jButtonOk.setBounds(new Rectangle(0, 145, 85, 20));
        jButtonCancel.setBounds(new Rectangle(90, 145, 85, 20));
        jButtonAggiorna.setBounds(new Rectangle(90, 145, 85, 20));
        jButtonRicalcola.setBounds(new Rectangle(90, 145, 85, 20));


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

        jButtonAggiorna.setText("Ripristina Valori");
        jButtonAggiorna.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                jButtonAggiorna_actionPerformed(e);
            }
        });

        jButtonRicalcola.setText("Ricalcola");
        jButtonRicalcola.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                jButtonRicalcola_actionPerformed(e);
            }
        });

        pBotton.add(jButtonOk);
        pBotton.add(jButtonRicalcola);
        pBotton.add(jButtonAggiorna);
        pBotton.add(jButtonCancel);
        pBotton.setMaximumSize(new Dimension(400, 20));

//        mainSplitPane.setTopComponent(sSplitPane);
//        mainSplitPane.setBottomComponent(pBotton);

        add(pBotton, BorderLayout.SOUTH);
        add(scrollPTab, BorderLayout.CENTER);
        add(scrollNq, BorderLayout.EAST);

    }

    void jButtonOk_actionPerformed(ActionEvent e) {
    
        try {
        SpallaManager manager = SpallaManager.getInstance();
        Spalla sp = manager.getCurrentSpalla();
        ArrayList vert = sp.getVerticaliIndagate();
        int ns = vert.size();
        
            double[] Nq = berPanel.getNq();
            for (int i = 0; i < ns; ++i) {
                Verticale v = (Verticale) vert.get(i);
                v.setNq(Nq[i]);
            }
            VerticaleIndagataCorrentePanel.getInstance().refreshView();
            this.dispose();

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Operazione non riuscita",
                    "Warning", JOptionPane.ERROR_MESSAGE);
        }




    }
      void jButtonAggiorna_actionPerformed(ActionEvent e) {
        berPanel.refreshView();
    }       

      void jButtonRicalcola_actionPerformed(ActionEvent e) {
         berPanel.ricalcola();
    }       
    void jButtonCancel_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}

