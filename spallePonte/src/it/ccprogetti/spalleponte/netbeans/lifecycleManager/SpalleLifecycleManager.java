/*
 * SpalleLifecycleManager.java
 *
 * Created on 11 marzo 2007, 19.44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.ccprogetti.spalleponte.netbeans.lifecycleManager;

import java.awt.event.ActionEvent;
import org.openide.LifecycleManager;
import progetto.presentation.controller.DefaultController;

/**
 *
 * @author andrea
 */
public class SpalleLifecycleManager extends LifecycleManager {
    
    private DefaultController controller = new DefaultController();
      private String actionCommand = "SalvaFile";       
    
    public void saveAll() {
         controller.actionPerformed( new ActionEvent( this , 0, actionCommand )  );
    }
    
    public void exit() {
        controller.actionPerformed( new ActionEvent( this , 0, actionCommand )  );
     }
       
    /** Creates a new instance of SpalleLifecycleManager */
    public SpalleLifecycleManager() { 
        controller.actionPerformed( new ActionEvent( this , 0, actionCommand )  );
    }
    
}
