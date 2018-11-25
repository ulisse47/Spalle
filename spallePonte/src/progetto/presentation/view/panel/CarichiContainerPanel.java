/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import it.ccprogetti.spalleponte.netbeans.actions.AddCaricoAction;
import it.ccprogetti.spalleponte.netbeans.actions.AddCombinazioneAction;
import it.ccprogetti.spalleponte.netbeans.actions.DeleteCaricoAction;
import it.ccprogetti.spalleponte.netbeans.actions.DeleteCombinazioneAction;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import org.openide.util.actions.SystemAction;
import org.openide.util.actions.SystemAction;
import progetto.model.bean.SpallaManager;
import progetto.presentation.view.table.TableCarichi;
import progetto.presentation.view.table.TableCombinazioni;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CarichiContainerPanel extends JPanel {

    private JSplitPane mainSplitPane;//horizontal
    private JSplitPane topSplitPane;//horizontal
    private JSplitPane bottomSplitPane;
    private JSplitPane bottomSplitPaneExternal;
    private JScrollPane ScrollPaneAzioniCarichi;//tabella carichi
    private JScrollPane topBottomScrollPane;//tabella combinazioni
    private JScrollPane bottomTopScrollPane;//tabella M1
    private JScrollPane bottomBottomScrollPane;//tabella M2
    private JScrollPane bottomMAppoggiScrollPane;//tabella M Appoggi
    private static CarichiContainerPanel panel;
    private Object[][] rowCarichiSelected;
    private Object[][] rowComboSelected;
    private JSplitPane SplitCarico;
 
    public static synchronized CarichiContainerPanel getInstance() {
        if (panel == null) {
            panel = new CarichiContainerPanel();
        }
        return panel;
    }

    /**
     * 
     */
    private void init() {


        mainSplitPane = new JSplitPane();
        mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        //bottomScrollPane = new JScrollPane();
        topSplitPane = new JSplitPane();
        topSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        bottomSplitPane = new JSplitPane();
        bottomSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        bottomSplitPaneExternal = new JSplitPane();
        bottomSplitPaneExternal.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        mainSplitPane.setBottomComponent( /*bottomScrollPane*/bottomSplitPaneExternal);
        mainSplitPane.setTopComponent(topSplitPane);

        bottomTopScrollPane = new JScrollPane();
        bottomBottomScrollPane = new JScrollPane();
        bottomMAppoggiScrollPane = new JScrollPane();

        SplitCarico = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        SplitCarico.setLeftComponent(caricoCorPan);
        SplitCarico.setBorder(new TitledBorder("Carichi"));
        ScrollPaneAzioniCarichi = new JScrollPane();
        ScrollPaneAzioniCarichi.setBorder(new TitledBorder("Azioni su appoggi"));
        ScrollPaneAzioniCarichi.getViewport().add(carichipanel);
        ScrollPaneAzioniCarichi.setPreferredSize(new Dimension(500, 100));
        SplitCarico.setRightComponent(ScrollPaneAzioniCarichi);

        topBottomScrollPane = new JScrollPane();
        topSplitPane.setTopComponent(SplitCarico);//tabella carichi
        //        topSplitPane.setTopComponent(topTopScrollPane);//tabella carichi
        topSplitPane.setBottomComponent(topBottomScrollPane);//tabella combinazioni

        bottomSplitPane.setLeftComponent(bottomTopScrollPane);//tabella M1 soll. zona incastro
        bottomSplitPane.setRightComponent(bottomBottomScrollPane);//tabella M2 soll. intradosso
        bottomSplitPaneExternal.setLeftComponent(bottomSplitPane);
        bottomSplitPaneExternal.setRightComponent(bottomMAppoggiScrollPane);


        topBottomScrollPane.setBorder(new TitledBorder("Combinazioni"));
        //      topTopScrollPane.setBorder(new TitledBorder("Azioni su appoggi"));
        bottomBottomScrollPane.setBorder(new TitledBorder("Sollecitazioni intradosso fondazione"));
        bottomTopScrollPane.setBorder(new TitledBorder("Sollecitazioni zona incastro"));
        bottomMAppoggiScrollPane.setBorder(new TitledBorder("Sollecitazioni appoggi"));

        topBottomScrollPane.getViewport().add(combopanel, BorderLayout.CENTER);
//	topBottomScrollPane.getViewport().add( jb, BorderLayout.WEST );

        topBottomScrollPane.setMinimumSize(new Dimension(100, 100));

        bottomBottomScrollPane.getViewport().add(new M2Panel());
        bottomTopScrollPane.getViewport().add(new M1Panel());
        bottomTopScrollPane.setMinimumSize(new Dimension(250, 100));
        bottomBottomScrollPane.setMinimumSize(new Dimension(250, 100));
        bottomMAppoggiScrollPane.getViewport().add(new MAppoggiPanel());


        setLayout(new BorderLayout());
        add(SplitCarico, BorderLayout.NORTH);
        //add( panelButton, BorderLayout.SOUTH );
        add(mainSplitPane, BorderLayout.CENTER);

    }

    /**
     *
     */
    private CarichiContainerPanel() {
        super();
        init();
        initComponents();

    }
    CarichiPanel carichipanel = new CarichiPanel();
    TableCarichi tabCarichi = carichipanel.getCarichi();
    JScrollPane scrollCarichi = carichipanel.getTableCarichiScrollPane();
    CombinazioniPanel combopanel = new CombinazioniPanel();
    TableCombinazioni tabCombo = combopanel.getTabCombo();
    JScrollPane scrollCombo = combopanel.getTableCombinazioniScrollPane();
    CaricoCorrentePanel caricoCorPan = CaricoCorrentePanel.getInstance();
    private javax.swing.JPopupMenu jPopupPanel;
    private javax.swing.JMenuItem menuCopyCarichi;
    private javax.swing.JMenuItem menuPasteCarichi;
    private javax.swing.JMenuItem menuCopyCombo;
    private javax.swing.JMenuItem menuPasteCombo;
    AddCaricoAction adCarico = SystemAction.get(AddCaricoAction.class);
    DeleteCaricoAction delCarico = SystemAction.get(DeleteCaricoAction.class);
    AddCombinazioneAction adCombo = SystemAction.get(AddCombinazioneAction.class);
    DeleteCombinazioneAction delCombo = SystemAction.get(DeleteCombinazioneAction.class);

    private void initComponents() {

        jPopupPanel = new JPopupMenu();

        ActionListener menuListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                if (event.getActionCommand().equals("copiaCarichi")) {
                    copiaCarichi();
                } else if (event.getActionCommand().equals("incollaCarichi")) {
                    incollaCarichi();
                } else if (event.getActionCommand().equals("copiaCombo")) {
                    copiaCombo();
                } else if (event.getActionCommand().equals("incollaCombo")) {
                    incollaCombo();
                }

                jPopupPanel.setVisible(false);
            }
        };


        menuCopyCarichi = new javax.swing.JMenuItem("Copia", new ImageIcon("it/ccprogetti" +
                "/spalleponte/netbeans/actions/images/Copy.gif"));
        menuPasteCarichi = new javax.swing.JMenuItem("Incolla", null);
        menuPasteCarichi.setActionCommand("incollaCarichi");
        menuCopyCarichi.setActionCommand("copiaCarichi");

        menuCopyCombo = new javax.swing.JMenuItem("Copia", new ImageIcon("it/ccprogetti" +
                "/spalleponte/netbeans/actions/images/Copy.gif"));
        menuPasteCombo = new javax.swing.JMenuItem("Incolla", null);
        menuPasteCombo.setActionCommand("incollaCombo");
        menuCopyCombo.setActionCommand("copiaCombo");


        //  popupTableTerreni.add(menuCopy);
        menuCopyCarichi.setHorizontalTextPosition(JMenuItem.RIGHT);
        menuCopyCarichi.addActionListener(menuListener);
        //popupTableTerreni.add(menuPaste);
        menuPasteCarichi.setHorizontalTextPosition(JMenuItem.RIGHT);
        menuPasteCarichi.addActionListener(menuListener);

        menuCopyCombo.setHorizontalTextPosition(JMenuItem.RIGHT);
        menuCopyCombo.addActionListener(menuListener);
        //popupTableTerreni.add(menuPaste);
        menuPasteCombo.setHorizontalTextPosition(JMenuItem.RIGHT);
        menuPasteCombo.addActionListener(menuListener);

        caricoCorPan.addMouseListener(new MousePopupListener());

        scrollCarichi.addMouseListener(new MousePopupListener());
        tabCarichi.addMouseListener(new MousePopupListener());

        scrollCombo.addMouseListener(new MousePopupListener());
        tabCombo.addMouseListener(new MousePopupListener());


    }

    private void incollaCombo() {
        try {
            int ns = rowComboSelected.length;
            int ncol = rowComboSelected[0].length;

            //righe selezionate dove copiare
            int nSel = tabCombo.getSelectedRowCount();
            if (nSel < 1) {
                return;
            }
            int sr = tabCombo.getSelectedRow();
            //        int size = tabCombo.getRowCount();
            int curPaste = sr;
            int curCopia = 0;

            for (int i = 0; i < nSel; ++i) {
                if (curCopia > ns - 1) {
                    curCopia = 0;
                }

                for (int j = 0; j < ncol; ++j) {
                    tabCombo.setValueAt(rowComboSelected[curCopia][j], curPaste, j);
                }
                curPaste += 1;
                curCopia += 1;
            }
            tabCombo.repaint();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "impossibile incollare",
                    "incolla righe", JOptionPane.INFORMATION_MESSAGE);
        }


    }

    private void copiaCombo() {
        try {

            int[] rs = tabCombo.getSelectedRows();
            int ncol = tabCombo.getColumnCount();
            int size = rs.length;
            rowComboSelected = new Object[size][ncol];
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < ncol; ++j) {
                    rowComboSelected[i][j] = tabCombo.getValueAt(rs[i], j);
                }

            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "impossibile copiare",
                    "copia righe", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void incollaCarichi() {
        try {
            //righe seleziane in precdente copia
            int ns = rowCarichiSelected.length;
            int ncol = rowCarichiSelected[0].length;

            //righe selezionate dove copiare
            int nSel = tabCarichi.getSelectedRowCount();
            if (nSel < 1) {
                return;
            }
            int sr = tabCarichi.getSelectedRow();
            //int size = tabCarichi.getRowCount();

            int curPaste = sr;
            int curCopia = 0;
            for (int i = 0; i < nSel; ++i) {
                if (curCopia > ns - 1) {
                    curCopia = 0;
                }

                for (int j = 1; j < ncol + 1; ++j) {
                    tabCarichi.setValueAt(rowCarichiSelected[curCopia][j - 1], curPaste, j);
                }
                curPaste += 1;
                curCopia += 1;
            }

            tabCarichi.repaint();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "impossibile incollare",
                    "inocolla righe", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void copiaCarichi() {
        try {
            int[] rs = tabCarichi.getSelectedRows();
            int ncol = tabCarichi.getColumnCount();
            int size = rs.length;
            rowCarichiSelected = new Object[size][ncol - 1];
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < ncol - 1; ++j) {
                    rowCarichiSelected[i][j] = tabCarichi.getValueAt(rs[i], j + 1);
                }

            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "impossibile copiare",
                    "copia righe", JOptionPane.INFORMATION_MESSAGE);
        }
    }


// An inner class to check whether mouse events are the popup trigger
    class MousePopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            checkPopup(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            checkPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            checkPopup(e);
        }

        private void checkPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                jPopupPanel.removeAll();
                jPopupPanel.add(adCarico);
                jPopupPanel.add(delCarico);
                jPopupPanel.addSeparator();
                jPopupPanel.add(adCombo);
                jPopupPanel.add(delCombo);
                if (e.getComponent() == scrollCombo) {
                    jPopupPanel.show(scrollCombo, e.getX(), e.getY());
                } else if (e.getComponent() == tabCombo) {
                    jPopupPanel.addSeparator();
                    jPopupPanel.add(menuCopyCombo);
                    jPopupPanel.add(menuPasteCombo);
                    jPopupPanel.show(tabCombo, e.getX(), e.getY());
                } else if (e.getComponent() == caricoCorPan) {
                    jPopupPanel.show(caricoCorPan, e.getX(), e.getY());
                } else if (e.getComponent() == tabCarichi) {
                    jPopupPanel.addSeparator();
                    jPopupPanel.add(menuCopyCarichi);
                    jPopupPanel.add(menuPasteCarichi);
                    jPopupPanel.show(tabCarichi, e.getX(), e.getY());
                } else if (e.getComponent() == scrollCarichi) {
                    jPopupPanel.show(scrollCarichi, e.getX(), e.getY());
                }
            //popup.show(VerificaPortanzaContainerPanel.this, e.getX(), e.getY());
            } else {
                jPopupPanel.setVisible(false);
            }

        }
    }

    
    

;

}
