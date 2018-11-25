/*
 * NoDataFoundException.java
 *
 * Created on 1 ottobre 2003, 19.54
 */

package progetto.exception;

/**
 *
 * @author  Andrea
 */
public class NoDataFoundException extends DataAccessException {
    
    /**
     * Creates a new instance of <code>NoDataFoundException</code> without detail message.
     */
    public NoDataFoundException( String msg ) {
        super(msg);
    }    
    
    /**
     * Constructs an instance of <code>NoDataFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoDataFoundException(String msg, Throwable pException ) {
        super(msg);
        this.exceptionCause = pException;
    }
    
}
