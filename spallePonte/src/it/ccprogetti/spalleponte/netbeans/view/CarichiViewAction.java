package it.ccprogetti.spalleponte.netbeans.view;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows CarichiView component.
 */
public class CarichiViewAction extends AbstractAction {
    
    public CarichiViewAction() {
        super(NbBundle.getMessage(CarichiViewAction.class, "CTL_CarichiViewAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(CarichiViewTopComponent.ICON_PATH, true)));
    }
    
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = CarichiViewTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
    
}
