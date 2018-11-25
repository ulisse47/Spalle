package it.ccprogetti.spalleponte.netbeans.view;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows FondazioniView component.
 */
public class FondazioniViewAction extends AbstractAction {
    
    public FondazioniViewAction() {
        super(NbBundle.getMessage(FondazioniViewAction.class, "CTL_FondazioniViewAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(FondazioniViewTopComponent.ICON_PATH, true)));
    }
    
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = FondazioniViewTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
    
}
