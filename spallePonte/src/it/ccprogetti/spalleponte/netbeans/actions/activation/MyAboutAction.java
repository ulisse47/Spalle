/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ccprogetti.spalleponte.netbeans.actions.activation;

import it.ccprogetti.spalleponte.netbeans.installer.dlgAbout;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class MyAboutAction extends CallableSystemAction {

    public void performAction() {
     try {
         dlgAbout dlg = new dlgAbout(null, true);
         dlg.setVisible(true);
     } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public String getName() {
        return NbBundle.getMessage(MyAboutAction.class, "CTL_MyAboutAction");
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
