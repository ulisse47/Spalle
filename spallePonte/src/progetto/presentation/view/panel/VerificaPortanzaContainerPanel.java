/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import it.ccprogetti.spalleponte.netbeans.actions.AddStratoTerrenoAction;
import it.ccprogetti.spalleponte.netbeans.actions.AddVerticaleAction;
import it.ccprogetti.spalleponte.netbeans.actions.RemoveStratoAction;
import it.ccprogetti.spalleponte.netbeans.actions.RemoveVerticaleAction;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import org.openide.util.actions.SystemAction;
import org.openide.util.actions.SystemAction;
import progetto.presentation.view.table.TableOutputForzeLaterali;
import progetto.presentation.view.table.TableRisultatiPortanza;
import progetto.presentation.view.table.TableTerreni;
import progetto.presentation.view.util.ViewComponent;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VerificaPortanzaContainerPanel extends JPanel implements ViewComponent {

    private JSplitPane mainSplitPane;//vertical
    private JSplitPane sSplitPane;//horizontal
    private JSplitPane tSplitPane;//horizontal
    private JScrollPane LeftScrollPane;
    private JScrollPane LeftScrollPaneBis;
    private JSplitPane risultatiSplitPane;//horizontal
    private JScrollPane StratiTerrenoScrollPane;
    private JScrollPane VerticaleCorrenteScrollPane;
    private Object[][] rowSelected;
    private JPopupMenu popupTableTerreni;
    private JPopupMenu popup;
    private static VerificaPortanzaContainerPanel containerPanel;

    public static synchronized VerificaPortanzaContainerPanel getInstance() {
        if (containerPanel == null) {
            containerPanel = new VerificaPortanzaContainerPanel();
        }
        return containerPanel;
    }

    /**
     * 
     */
    private void init() {
        mainSplitPane = new JSplitPane();
        mainSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        sSplitPane = new JSplitPane();
        sSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        tSplitPane = new JSplitPane();
        tSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        risultatiSplitPane = new JSplitPane();
        risultatiSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        LeftScrollPane = new JScrollPane();
        LeftScrollPane.setBorder(new TitledBorder("Risultati Portanza verticale"));
        LeftScrollPane.getViewport().add(TableRisultatiPortanza.getInstance());
        LeftScrollPane.setMinimumSize(new Dimension(50, 100));
        LeftScrollPaneBis = new JScrollPane();
        LeftScrollPaneBis.setBorder(new TitledBorder("Risultati carichi trsversali"));
        LeftScrollPaneBis.getViewport().add(TableOutputForzeLaterali.getInstance());
        LeftScrollPaneBis.setMinimumSize(new Dimension(50, 100));
        risultatiSplitPane.setLeftComponent(LeftScrollPaneBis);
        risultatiSplitPane.setRightComponent(LeftScrollPane);
        
        StratiTerrenoFondazioniView pannelloDisegno = StratiTerrenoFondazioniView.getInstance();

        StratiTerrenoScrollPane = new JScrollPane();
        StratiTerrenoScrollPane.setBorder(new TitledBorder("Stratigrafia verticale indagata"));
        StratiTerrenoScrollPane.getViewport().add(tabTerreno);
        StratiTerrenoScrollPane.setMinimumSize(new Dimension(50, 100));

        VerticaleCorrenteScrollPane = new JScrollPane();
        //       VerticaleCorrenteScrollPane.setBorder(new TitledBorder("Dati verticale"));
        VerticaleCorrenteScrollPane.getViewport().add(VerticaleIndagataCorrentePanel.getInstance());
//        VerticaleCorrenteScrollPane.setMinimumSize(new Dimension(50, 100));


        tSplitPane.setTopComponent(VerticaleCorrenteScrollPane);
        tSplitPane.setBottomComponent(StratiTerrenoScrollPane);
        tSplitPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        sSplitPane.setTopComponent(tSplitPane);//disegno marco
        sSplitPane.setBottomComponent(pannelloDisegno);//disegno marco
        sSplitPane.setBackground(Color.BLACK);

        mainSplitPane.setRightComponent(risultatiSplitPane);
        mainSplitPane.setLeftComponent(sSplitPane);

        setLayout(new BorderLayout());
        add(mainSplitPane, BorderLayout.CENTER);

        initComponents();
    }
    private javax.swing.JMenuItem menuCopy;
    private javax.swing.JMenuItem menuPaste;
    TableTerreni tabTerreno = TableTerreni.getInstance();
    JPanel pVerticale;

    private void initComponents() {

        popupTableTerreni = new JPopupMenu();
        popup = new JPopupMenu();

        ActionListener menuListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                if (event.getActionCommand().equals("Copia")) {
                    copia();
                } else if (event.getActionCommand().equals("Incolla")) {
                    incolla();
                }

                popupTableTerreni.setVisible(false);
            }
        };

        AddVerticaleAction aver = SystemAction.get(AddVerticaleAction.class);
        RemoveVerticaleAction rver = SystemAction.get(RemoveVerticaleAction.class);

        AddStratoTerrenoAction acar = SystemAction.get(AddStratoTerrenoAction.class);
        RemoveStratoAction rcar = SystemAction.get(RemoveStratoAction.class);

        menuCopy = new javax.swing.JMenuItem("Copia", new ImageIcon("it/ccprogetti" +
                "/spalleponte/netbeans/actions/images/Copy.gif"));
        menuPaste = new javax.swing.JMenuItem("Incolla", null);

        popupTableTerreni.add(aver);
        popupTableTerreni.add(rver);
        popupTableTerreni.addSeparator();
        popupTableTerreni.add(acar);
        popupTableTerreni.add(rcar);
        popupTableTerreni.addSeparator();

        popup.add(aver);
        popup.add(rver);
        popup.addSeparator();
        popup.add(acar);
        popup.add(rcar);

        popupTableTerreni.add(menuCopy);
        menuCopy.setHorizontalTextPosition(JMenuItem.RIGHT);
        menuCopy.addActionListener(menuListener);
        popupTableTerreni.add(menuPaste);
        menuPaste.setHorizontalTextPosition(JMenuItem.RIGHT);
        menuPaste.addActionListener(menuListener);

        StratiTerrenoScrollPane.addMouseListener(new MousePopupListener());
        tabTerreno.addMouseListener(new MousePopupListener());
        VerticaleCorrenteScrollPane.addMouseListener(new MousePopupListener());

    }

    private void incolla() {
        int ns = rowSelected.length;
        int ncol = rowSelected[0].length;

        //righe selezionate dove copiare
        int nSel = tabTerreno.getSelectedRowCount();
        if (nSel < 1) {
            return;
        }
        int sr = tabTerreno.getSelectedRow();

        int curPaste = sr;
        int curCopia = 0;

        for (int i = 0; i < nSel; ++i) {
            if (curCopia > ns - 1) {
                curCopia = 0;
            }
            for (int j = 1; j < ncol; ++j) {
                tabTerreno.setValueAt(rowSelected[curCopia][j - 1], curPaste, j);
            }
            curPaste += 1;
            curCopia += 1;
        }


        tabTerreno.repaint();
    }

    private void copia() {
        int[] rs = tabTerreno.getSelectedRows();
        int size = rs.length;
        rowSelected = new Object[size][6];
        for (int i = 0; i <
                size; ++i) {
            for (int j = 0; j < 6; ++j) {
                rowSelected[i][j] = tabTerreno.getValueAt(rs[i], j + 1);
            }

        }
    }

    /**
     *
     */
    private VerificaPortanzaContainerPanel() {
        super();
        init();

    }

    /* (non-Javadoc)
     * @see progetto.presentation.view.util.ViewComponent#refreshView()
     */
    public void refreshView() {
        TableTerreni.getInstance().refreshView();
        VerticaleIndagataCorrentePanel.getInstance().refreshView();
        super.repaint();
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
                if (e.getComponent() == StratiTerrenoScrollPane) {
                    popupTableTerreni.show(StratiTerrenoScrollPane, e.getX(), e.getY());
                } else if (e.getComponent() == VerticaleCorrenteScrollPane) {
                    popup.show(VerticaleCorrenteScrollPane, e.getX(), e.getY());
                } else if (e.getComponent() == tabTerreno) {
                    popupTableTerreni.show(tabTerreno, e.getX(), e.getY());
                }
            //popup.show(VerificaPortanzaContainerPanel.this, e.getX(), e.getY());
            } else {
                popupTableTerreni.setVisible(false);
                popup.setVisible(false);
            }

        }
    }

    
    

;
}
