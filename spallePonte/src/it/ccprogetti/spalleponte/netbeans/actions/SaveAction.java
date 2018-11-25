package it.ccprogetti.spalleponte.netbeans.actions;

import it.ccprogetti.activation.core.StartUpExt;
import java.awt.event.ActionEvent;
import org.netbeans.core.windows.WindowManagerImpl;
import org.netbeans.core.windows.view.ui.MainWindow;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.controller.DefaultController;

@ActionID(id = "it.ccprogetti.spalleponte.netbeans.actions.SaveAction", category = "Edit")
//@ActionRegistration(displayName = "#TODO", lazy = false)

public final class SaveAction extends CallableSystemAction {
    
     
    private DefaultController controller = new DefaultController();
    
    private String actionCommand = "SalvaFile";
    
    private void setTitle(){
        if ( SpalleBusinessDelegateImpl.getInstance().getFileCorrente() != null ){
          
            String title = NbBundle.getMessage(MainWindow.class, "CTL_MainWindow_Title", System.getProperty("netbeans.buildnumber"));
            WindowManagerImpl.getInstance().getMainWindow().setTitle( title + " - " + SpalleBusinessDelegateImpl.getInstance().getFileCorrente().getPath() );
         }
    }
    
    public void performAction() {
        // TODO implement action body
    }
    
      
     public void actionPerformed(ActionEvent actionEvent) {
        
        if (SpalleBusinessDelegateImpl.getInstance().getMode() == StartUpExt.DEMO) {
            NotifyDescriptor d = new NotifyDescriptor.Message("Il salvataggio del progetto è consentito solo alla versione registrata del programma", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
            return;
        }          
         
        controller.actionPerformed( new ActionEvent( actionEvent.getSource(), 0, actionCommand )  );
        setTitle();
     }
    
    public String getName() {
        return NbBundle.getMessage(SaveAction.class, "CTL_SaveAction");
    }
    
    protected String iconResource() {
        return "it/ccprogetti/spalleponte/netbeans/actions/save.png";
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}
