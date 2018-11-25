package progetto.presentation.util;

import java.io.IOException;

import progetto.presentation.dispatcher.RequestDispatcherInt;


/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 13-set-2003
 * Time: 15.43.44
 * To change this template use Options | File Templates.
 */
public interface Command {

    public RequestDispatcherInt execute(RequestHelper helper) throws Exception,
            IOException;

}
