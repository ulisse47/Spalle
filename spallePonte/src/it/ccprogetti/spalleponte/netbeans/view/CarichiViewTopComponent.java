package it.ccprogetti.spalleponte.netbeans.view;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.JPopupMenu;
import org.openide.ErrorManager;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import progetto.presentation.view.panel.CarichiContainerPanel;

/**
 * Top component which displays something.
 */
final class CarichiViewTopComponent extends TopComponent {
    
    public static CarichiViewTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    
    private static final String PREFERRED_ID = "CarichiViewTopComponent";
    
    private CarichiViewTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(CarichiViewTopComponent.class, "CTL_CarichiViewTopComponent"));
        setToolTipText(NbBundle.getMessage(CarichiViewTopComponent.class, "HINT_CarichiViewTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized CarichiViewTopComponent getDefault() {
        if (instance == null) {
            instance = new CarichiViewTopComponent();
        }
        return instance;
    }
    
    /**
     * Obtain the CarichiViewTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized CarichiViewTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            ErrorManager.getDefault().log(ErrorManager.WARNING, "Cannot find CarichiView component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof CarichiViewTopComponent) {
            return (CarichiViewTopComponent)win;
        }
        ErrorManager.getDefault().log(ErrorManager.WARNING, "There seem to be multiple components with the '" + PREFERRED_ID + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }
    
    public void componentOpened() {
       add( CarichiContainerPanel.getInstance() );
    }
    
    public void componentClosed() {
        // TODO add custom code on component closing
    }
    
    /** replaces this in object stream */
    public Object writeReplace() {
        return new ResolvableHelper();
    }
    
    protected String preferredID() {
        return PREFERRED_ID;
    }
    
    final static class ResolvableHelper implements Serializable {
        private static final long serialVersionUID = 1L;
        public Object readResolve() {
            return CarichiViewTopComponent.getDefault();
        }
    }
    
}
