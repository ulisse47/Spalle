/*
 * ServiceLocatorException.java
 *
 * Created on 4 ottobre 2003, 12.06
 */

package progetto.exception;

/**
 *
 * @author  Andrea
 */
public class ServiceLocatorException extends DataAccessException {
    
        
    /**
     * Constructs an instance of <code>ServiceLocatorException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ServiceLocatorException(String msg) {
        super( msg );
    }
    
    public ServiceLocatorException( String msg, Throwable pException ) {
        super( msg );
        this.exceptionCause = pException;
    }    
}
