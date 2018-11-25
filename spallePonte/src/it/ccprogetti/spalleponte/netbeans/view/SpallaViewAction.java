package it.ccprogetti.spalleponte.netbeans.view;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows SpallaView component.
 */
public class SpallaViewAction extends AbstractAction {
    
    
    public SpallaViewAction() {
        super(NbBundle.getMessage(SpallaViewAction.class, "CTL_SpallaViewAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(SpallaViewTopComponent.ICON_PATH, true)));
    }
    
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = SpallaViewTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
    
}
