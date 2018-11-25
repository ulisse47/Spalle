/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ccprogetti.spalleponte.netbeans.actions.activation;

import it.ccprogetti.activation.core.StartUp;
import it.ccprogetti.activation.core.StartUpExt;
import java.awt.Desktop;
import java.net.URI;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class FlaccovioWebAction extends CallableSystemAction {

    public void performAction() {
         try {
            // StartUpExt.getInstance().CCProgettiWEB();
            java.awt.Desktop desk;
            desk = Desktop.getDesktop();
            desk.browse(  new URI ("http://www.ccprogetti.it") );

             
             
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public String getName() {
        return NbBundle.getMessage(FlaccovioWebAction.class, "CTL_FlaccovioWebAction");
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
