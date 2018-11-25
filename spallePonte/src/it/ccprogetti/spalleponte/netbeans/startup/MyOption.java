/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.ccprogetti.spalleponte.netbeans.startup;

import it.ccprogetti.spalleponte.netbeans.actions.OpenAction;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.netbeans.api.sendopts.CommandException;
import org.netbeans.spi.sendopts.Env;
import org.netbeans.spi.sendopts.Option;
import org.netbeans.spi.sendopts.OptionProcessor;
import org.openide.util.actions.CallableSystemAction;

/**
 *
 * @author andrea.cavalieri
 */
public class MyOption extends OptionProcessor {
    
   private static Option name = Option.requiredArgument(Option.NO_SHORT_NAME, "file");

   public Set getOptions() {
    return Collections.singleton(name);
}

public void process(Env env, Map values) throws CommandException {
  //System.exit(0);
    
    String[] args = (String[]) values.get(name );
    if (args.length > 0) {
        OpenAction action = CallableSystemAction.findObject( OpenAction.class );
        action.performAction( args[0] );
       
        //new OpenAction().performAction( args[0] );
        //StatusDisplayer.getDefault().setStatusText("Hello " + args[0]);
        //SomeAction.setName(args[0]);
   }
}

}
