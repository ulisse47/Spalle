package it.ccprogetti.spalleponte.netbeans.actions;

import it.ccprogetti.activation.core.StartUp;
import it.ccprogetti.activation.core.StartUpExt;
import java.awt.event.ActionEvent;
import org.netbeans.core.windows.WindowManagerImpl;
import org.netbeans.core.windows.view.ui.MainWindow;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.controller.DefaultController;

public final class SaveAsAction extends CallableSystemAction {
   
    private DefaultController controller = new DefaultController();
    
    private String actionCommand = "salvaCome";
    
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
        return NbBundle.getMessage(SaveAsAction.class, "CTL_SaveAsAction");
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
