/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.ccprogetti.spalleponte.netbeans.actions.activation;

import it.ccprogetti.activation.core.StartUpExt;
import java.awt.Desktop;
import java.net.URI;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class FiloDirettoAction extends CallableSystemAction {

    public void performAction() {
        try {           
            java.awt.Desktop desk;
            desk = Desktop.getDesktop();
            String uri =  "https://download.ccprogetti.it/index.php/assistenza";
//            uri+= "?body= Codice di attivazione: " + StartUpExt.getInstance().readActivationCodeFromMaya();
//            uri+= "\n User ID: " + StartUpExt.getInstance().getUser();
            desk.browse(  new URI (uri) );
            
//desk.browse(  new URI ("mailto:assistenza@ccprogetti.it?subject=CODICEXYZ") );
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public String getName() {
        return NbBundle.getMessage(FiloDirettoAction.class, "CTL_FiloDirettoAction");
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
