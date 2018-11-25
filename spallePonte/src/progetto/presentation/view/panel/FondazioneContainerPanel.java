/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import it.ccprogetti.spalleponte.netbeans.actions.AddPaloAction;
import it.ccprogetti.spalleponte.netbeans.actions.DeletePaloAction;
import it.ccprogetti.spalleponte.netbeans.actions.RimuoviPaliAction;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import org.openide.util.actions.SystemAction;
import progetto.presentation.view.table.TablePali;
import progetto.presentation.view.util.ViewComponent;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FondazioneContainerPanel extends JPanel implements ViewComponent {

    private JSplitPane mainSplitPane;//horizontal
    private JScrollPane paliScrollPane;
    private JSplitPane topSplitPane;//horizontal
    private JScrollPane topLeftScrollPane;//tabella carichi
    private JSplitPane llspl;//tabella carichi
    private static FondazioneContainerPanel containerPanel;

    public static synchronized FondazioneContainerPanel getInstance() {
        if (containerPanel == null) {
            containerPanel = new FondazioneContainerPanel();
        }
        return containerPanel;
    }

    /**
     * 
     */
    private void init() {
        mainSplitPane = new JSplitPane();
        mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        topSplitPane = new JSplitPane();
        topSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        topLeftScrollPane = new JScrollPane();
        topLeftScrollPane.setBorder(new TitledBorder(""));

        llspl = new JSplitPane();
        llspl.setOrientation(JSplitPane.VERTICAL_SPLIT);

        topLeftScrollPane.getViewport().add(PalificataOutputDataPanel.getInstance());
        topLeftScrollPane.setMinimumSize(new Dimension(50, 100));

//        llspl.setTopComponent(ComboCorrentePanel.getInstance());
          llspl.setTopComponent(ComboCorrentePanel.getInstance());
        
        llspl.setBottomComponent(topLeftScrollPane);


        topSplitPane.setLeftComponent(llspl);
        FondazioniView pannelloDisegno = FondazioniView.getInstance();
        //pannelloDisegno.setBorder( new TitledBorder( "Disegno pali" ) );
        topSplitPane.setRightComponent(pannelloDisegno);//disegno marco
        topSplitPane.setBackground(Color.BLACK);

        paliScrollPane = new JScrollPane();
        paliScrollPane.setBorder(new TitledBorder("Palificata"));

        paliScrollPane.getViewport().add(table);
        paliScrollPane.setMinimumSize(new Dimension(50, 100));

        mainSplitPane.setTopComponent(topSplitPane);
        mainSplitPane.setBottomComponent(paliScrollPane);


        setLayout(new BorderLayout());
        add(mainSplitPane, BorderLayout.CENTER);

    }

    /**
     *
     */
    private FondazioneContainerPanel() {
        super();
        init();
        initComponents();

    }
    TablePali table = TablePali.getInstance();
    private javax.swing.JPopupMenu jPopupPanel;
    AddPaloAction adPalo = SystemAction.get(AddPaloAction.class);
    DeletePaloAction delPalo = SystemAction.get(DeletePaloAction.class);
    RimuoviPaliAction clearPali = SystemAction.get(RimuoviPaliAction.class);
   private Object[][] rowSelected;
    private javax.swing.JMenuItem menuCopy;
    private javax.swing.JMenuItem menuPaste;

    
    
    private void initComponents() {

        jPopupPanel = new JPopupMenu();

        ActionListener menuListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                if (event.getActionCommand().equals("copia")) {
                copiaPali();
                } else if (event.getActionCommand().equals("incolla")) {
                incollaPali();
                } 
                jPopupPanel.setVisible(false);
            }
        };


        menuCopy = new javax.swing.JMenuItem("Copia", new ImageIcon("it/ccprogetti" +
        "/spalleponte/netbeans/actions/images/Copy.gif"));
        menuPaste = new javax.swing.JMenuItem("Incolla", null);
        menuPaste.setActionCommand("incolla");
        menuCopy.setActionCommand("copia");
        //  popupTableTerreni.add(menuCopy);
        menuCopy.setHorizontalTextPosition(JMenuItem.RIGHT);
        menuCopy.addActionListener(menuListener);
        //popupTableTerreni.add(menuPaste);
        menuPaste.setHorizontalTextPosition(JMenuItem.RIGHT);
        menuPaste.addActionListener(menuListener);

        table.addMouseListener(new MousePopupListener());
        paliScrollPane.addMouseListener(new MousePopupListener());


    }

       private void incollaPali() {
        try {
            
            //righe seleziane in precdente copia
            int ns = rowSelected.length;
            int ncol = rowSelected[0].length;
            
            //righe selezionate dove copiare
            int sr = table.getSelectedRow();
            int nSel = table.getSelectedRowCount();
            if (nSel<1) return;
            
            //int size = table.getRowCount();
            int curPaste = sr;
            int curCopia = 0;
            for (int i = 0; i < nSel; ++i) {
                if (curCopia > ns-1) curCopia =0;
                for (int j = 1; j < ncol+1 ; ++j) {
                    table.setValueAt(rowSelected[curCopia][j - 1], curPaste, j);
                }
                curCopia +=1;
                curPaste += 1;
            }
            table.repaint();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "impossibile incollare",
                    "incolla righe", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void copiaPali() {
        try {
            int[] rs = table.getSelectedRows();
            int ncol = table.getColumnCount();
            int size = rs.length;
            rowSelected = new Object[size][ncol - 2];
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < ncol - 2; ++j) {
                    rowSelected[i][j] = table.getValueAt(rs[i], j + 1);
                }

            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "impossibile copiare",
                    "copia righe", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    /* (non-Javadoc)
     * @see progetto.presentation.view.util.ViewComponent#refreshView()
     */
    public void refreshView() {
        TablePali.getInstance().refreshView();
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
                jPopupPanel.removeAll();
                jPopupPanel.add(adPalo);
                jPopupPanel.add(delPalo);
                jPopupPanel.add(clearPali);
   
                if (e.getComponent() == table) {
                    jPopupPanel.addSeparator();
                    jPopupPanel.add(menuCopy);
                    jPopupPanel.add(menuPaste);
                    jPopupPanel.show(table, e.getX(), e.getY());
                } else if (e.getComponent() == paliScrollPane) {
                    jPopupPanel.show(paliScrollPane, e.getX(), e.getY());

                }
            //popup.show(VerificaPortanzaContainerPanel.this, e.getX(), e.getY());
            } else {
                jPopupPanel.setVisible(false);
            }

        }
    }

    
    

;
}
