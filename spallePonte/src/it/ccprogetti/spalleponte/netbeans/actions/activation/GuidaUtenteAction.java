/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ccprogetti.spalleponte.netbeans.actions.activation;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class GuidaUtenteAction extends CallableSystemAction {

    public void performAction() {
        try {
            java.awt.Desktop desk;
            desk = Desktop.getDesktop();
            File guida;
            guida = new File(System.getProperty("user.dir") + "\\help\\Help.html");
            desk.open(guida);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public String getName() {
        return NbBundle.getMessage(GuidaUtenteAction.class, "CTL_GuidaUtenteAction");
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
