package it.ccprogetti.spalleponte.netbeans.actions;

import java.awt.event.ActionEvent;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.controller.DefaultController;

public final class WizardPalificataAction extends CallableSystemAction {
    
    private DefaultController controller = new DefaultController();
    
    private String actionCommand = "wizardPalificata";       
            
    public void actionPerformed(ActionEvent actionEvent) {
        controller.actionPerformed( new ActionEvent( actionEvent.getSource(), 0, actionCommand )  );
    }
    public void performAction() {
        // TODO implement action body
    }
    
    public String getName() {
        return NbBundle.getMessage(WizardPalificataAction.class, "CTL_WizardPalificataAction");
    }
    
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}
