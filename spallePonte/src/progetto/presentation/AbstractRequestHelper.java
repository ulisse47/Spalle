package progetto.presentation;

import java.awt.event.ActionEvent;

import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 13-set-2003
 * Time: 15.55.09
 * To change this template use Options | File Templates.
 */
abstract class AbstractRequestHelper implements RequestHelper{
    private ActionEvent request = null;
    
    public AbstractRequestHelper ( ActionEvent request ){
        this.request = request;
    }
 
	public ActionEvent getRequest(){
        return request;
    }   

    public abstract Command getCommand();   

}
