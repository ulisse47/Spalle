package progetto.presentation;



import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import progetto.presentation.util.Command;
import progetto.presentation.util.CommandFactory;
import progetto.presentation.util.ObjectCreator;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 13-set-2003
 * Time: 17.14.51
 * To change this template use Options | File Templates.
 */
public class CommandFactoryImpl implements CommandFactory {

    private HashMap commands = new HashMap();
    private final static String ACTION_MAPPING_PROPERTIES = "actionMapping.properties";
    private static CommandFactory commandFactory;
    
    /**
     * 
     * @return
     */
    public static synchronized CommandFactory getInstance(){
    	if ( commandFactory == null ){
    		commandFactory = new CommandFactoryImpl();
    	}
    	return commandFactory;
    }
    
    private CommandFactoryImpl() {
        try{
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream(ACTION_MAPPING_PROPERTIES));
          
            for(Enumeration e = properties.keys(); e.hasMoreElements(); ){
                String action = ( String )e.nextElement();
                commands.put ( action, ObjectCreator.createObject(
                        properties.getProperty( action ) ) ); 
            }

        }catch(Exception e){
            System.out.println("Error: " + e.toString() );
            e.printStackTrace();
        }

    }
    public Command createCommand(String action){
        return ( Command )commands.get( action );
    }



}
