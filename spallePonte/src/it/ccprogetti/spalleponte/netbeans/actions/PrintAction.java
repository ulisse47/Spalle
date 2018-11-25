package it.ccprogetti.spalleponte.netbeans.actions;

import it.ccprogetti.activation.core.StartUp;
import it.ccprogetti.activation.core.StartUpExt;
import java.awt.event.ActionEvent;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.controller.DefaultController;

public final class PrintAction extends CallableSystemAction {    
    
    private DefaultController controller = new DefaultController();
    
    private String actionCommand = "stampa";
    
    public void actionPerformed( ActionEvent actionEvent ) { 
        if (SpalleBusinessDelegateImpl.getInstance().getMode() == StartUpExt.DEMO) {
            return;
        }
        controller.actionPerformed( new ActionEvent( actionEvent.getSource(), 0, actionCommand ) );
    }
      
   public void performAction() {
        // TODO implement action body
    }
    
    public String getName() {
        return NbBundle.getMessage(PrintAction.class, "CTL_PrintAction");
    }
    
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        //putValue("noIconInMenu", Boolean.TRUE);
    }
    
     protected String iconResource() {
        return "it/ccprogetti/spalleponte/netbeans/actions/print.png";
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}
