/*
 * DataAccessException.java
 *
 * Created on 1 ottobre 2003, 19.41
 */

package progetto.exception;

import java.io.PrintWriter;

/**
 *
 * @author  Andrea
 */
public class DataAccessException extends Exception {
    
    protected Throwable exceptionCause = null;
    
    /**
     * Creates a new instance of <code>DataAccessException</code> without detail message.
     */
    public DataAccessException( String msg ) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>DataAccessException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DataAccessException(String msg, Throwable pException ) {
        super(msg);
        this.exceptionCause = pException;
    }

    public void printStackTrace ( PrintWriter s ) {
        if ( exceptionCause != null ) {
            System.err.println( "An exception has been caused by: " );
            exceptionCause.printStackTrace( s );
        }
        else {
            super.printStackTrace( s );
        }

    }

    public void printStackTrace() {
        if ( exceptionCause != null ) {
            System.err.println( "An exception has been caused by: " );
            exceptionCause.printStackTrace();
        }
        else {
            super.printStackTrace();
        }
    }
    
}
