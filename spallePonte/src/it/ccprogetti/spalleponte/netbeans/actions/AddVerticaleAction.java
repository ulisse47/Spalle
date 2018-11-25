package it.ccprogetti.spalleponte.netbeans.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import org.openide.LifecycleManager;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.controller.DefaultController;

public final class AddVerticaleAction extends CallableSystemAction {
    
    private DefaultController controller = new DefaultController();
    
    private String actionCommand = "addVerticale";       
            
    public void actionPerformed(ActionEvent actionEvent) {
        Collection c = Lookup.getDefault().lookup( new Lookup.Template(LifecycleManager.class)).allInstances();
        
        for (Iterator i=c.iterator(); i.hasNext();) { 
            LifecycleManager s = (LifecycleManager) i.next(); 
            System.out.println( s.getClass() );
            
        }
        
        
        controller.actionPerformed( new ActionEvent( actionEvent.getSource(), 0, actionCommand )  );
    }
    
    public void performAction() {
        // TODO implement action body
    }
    
    public String getName() {
        return NbBundle.getMessage(AddVerticaleAction.class, "CTL_addVerticaleAction");
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
