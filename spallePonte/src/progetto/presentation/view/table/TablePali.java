/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.table;

import java.awt.Dimension;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import progetto.presentation.view.components.AbstractBaseTable;
import progetto.presentation.view.components.TableModelPali;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TablePali extends AbstractBaseTable {

    private static TablePali table;

    public static synchronized TablePali getInstance() {
        if (table == null) {
            table = new TablePali();
        }
        return table;
    }

    /**
     * 
     */
    private TablePali() {
        super();
     //   initComponents();
        this.setPreferredScrollableViewportSize(new Dimension(200, 100));
    }
  


    /*       jPopupSpalla.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
    jPopupSpallaMouseClicked(evt);
    }
    });
    jMenuItemAddAppoggio.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseReleased(java.awt.event.MouseEvent evt) {
    jMenuItemAddAppoggioMouseReleased(evt);
    }
    });
    setComponentPopupMenu(jPopupSpalla);
    addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
    formMouseClicked(evt);
    }
    });
    }// </editor-fold>    
    // Variables declaration - do not modify                     
    private javax.swing.JMenuItem jMenuItemAddAppoggio;
    private javax.swing.JPopupMenu jPopupSpalla;
    // End of variables declaration       
    private void jPopupSpallaMouseClicked(java.awt.event.MouseEvent evt) {
    // TODO addg your handling code here:
    }
    private void formMouseClicked(java.awt.event.MouseEvent evt) {
    // TODO addg your handling code here:
    }
    private void jMenuItemAddAppoggioMouseReleased(java.awt.event.MouseEvent evt) {
    // TODO add your handling code here:
    }
     */
    /* (non-Javadoc)
     * @see progetto.presentation.view.components.AbstractBaseTable#loadModel()
     */
    protected TableModel loadModel() {
        TableModelPali model = new TableModelPali();
        return model;
    }
}
