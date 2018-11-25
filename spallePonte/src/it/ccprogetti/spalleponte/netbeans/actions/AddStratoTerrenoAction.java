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

public final class AddStratoTerrenoAction extends CallableSystemAction {
    
    private DefaultController controller = new DefaultController();
    
    private String actionCommand = "addStratoTerreno";       
            
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
        return NbBundle.getMessage(AddStratoTerrenoAction.class, "CTL_AggiungiStratoTerrenoAction");
    }
    
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }
    
/*     protected String iconResource() {
        return "it/ccprogetti/spalleponte/netbeans/actions/images/palo.gif";
    }
  */  
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}
