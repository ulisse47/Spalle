package it.ccprogetti.spalleponte.netbeans.actions;

import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import org.netbeans.core.windows.WindowManagerImpl;
import org.netbeans.core.windows.view.ui.MainWindow;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.commands.LoadFromFileCommand;
import progetto.presentation.controller.DefaultController;

public final class OpenAction extends CallableSystemAction {
     
    
    private DefaultController controller = new DefaultController();
    
    private void setTitle() {
        
        if (SpalleBusinessDelegateImpl.getInstance().getFileCorrente() != null) {           

                Thread t = new Thread() {

                @Override
                    public void run() {
                        String title = NbBundle.getMessage(MainWindow.class, "CTL_MainWindow_Title", System.getProperty("netbeans.buildnumber"));
                        WindowManagerImpl.getInstance().getMainWindow().setTitle(title + " - " + SpalleBusinessDelegateImpl.getInstance().getFileCorrente().getPath());
                    }
                };

                if (   SwingUtilities.isEventDispatchThread() ){
                    t.run();
                }
                else{
                     SwingUtilities.invokeLater( t );
                }                    
        }
    }
    
    private String actionCommand = "open";
      
   public void performAction() {
        // TODO implement action body
    }
   
   
    public void performAction( String fileName ) {
        try {
            LoadFromFileCommand command = new LoadFromFileCommand();
            command.openFile( fileName );            
            setTitle();
            
            command.forward(null);
            
            //WindowManagerImpl.getInstance().getMainWindow().setTitle( Progetto.getInstance().getFileCorrente().getPath() );
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        //WindowManagerImpl.getInstance().getMainWindow().setTitle( Progetto.getInstance().getFileCorrente().getPath() );
    }
    

    public void actionPerformed( ActionEvent actionEvent ) { 
        controller.actionPerformed( new ActionEvent( actionEvent.getSource(), 0, actionCommand ) );
        setTitle();
    }
    
    public String getName() {
        return NbBundle.getMessage(OpenAction.class, "CTL_OpenAction");
    }
    
    protected String iconResource() {
        return "it/ccprogetti/spalleponte/netbeans/actions/openProject.gif";
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}
