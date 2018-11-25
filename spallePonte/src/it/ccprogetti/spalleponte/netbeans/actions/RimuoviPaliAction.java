/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ccprogetti.spalleponte.netbeans.actions;

import java.awt.event.ActionEvent;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.controller.DefaultController;

public final class RimuoviPaliAction extends CallableSystemAction {

    private DefaultController controller = new DefaultController();
    
    private String actionCommand = "eliminaTuttiPali";
 
    
    public void performAction() {
       //to do
     }

    public void actionPerformed(ActionEvent actionEvent) {
        controller.actionPerformed( new ActionEvent( actionEvent.getSource(), 0, actionCommand )  );
    }
 
    public String getName() {
        return NbBundle.getMessage(RimuoviPaliAction.class, "CTL_RimuoviPaliAction");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
