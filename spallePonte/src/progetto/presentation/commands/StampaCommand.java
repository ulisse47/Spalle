package progetto.presentation.commands;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.table.TableAppoggi;
import progetto.presentation.view.table.TableCarichi;
import progetto.util.FileChooser;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class StampaCommand implements Command, RequestDispatcherInt  {


    private SpalleBusinessDelegate bDelegate = 
    	SpalleBusinessDelegateImpl.getInstance();

    public StampaCommand () { }

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage ( Properties properties ) {
        return "Stampa";
    }

    /**
     *
     * @param helper
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public synchronized RequestDispatcherInt execute ( RequestHelper helper ) throws Exception,
            IOException {
      try {
           //componente businness
          String fileName;
          FileChooser chooser = new FileChooser(JFileChooser.SAVE_DIALOG, "rtf", "");
          String currentPath =  bDelegate.getPathCorrente();
          File currDir = new File(currentPath);
          if(currDir.exists()) chooser.setCurrentDirectory(currDir);
  
          chooser.setDialogTitle("Salvataggio della relazione di calcolo");
          chooser.setDialogType( JFileChooser.SAVE_DIALOG ); 
          chooser.setTipo(FileChooser.SAVE_AS);
          //chooser.setCurrentDirectory( getFileCorrente() );
          int returnVal = chooser.showDialog( null, "Seleziona" );
          if (returnVal == FileChooser.APPROVE_OPTION) {
              fileName = chooser.getAbsoluteFileName();
              bDelegate.setPathCorrente(new File(fileName).getAbsolutePath());
              bDelegate.stampa(fileName);
              Desktop.getDesktop().open(new File( fileName ));
          }          
            
                                         
        } catch ( Exception ex ) {
            System.out.println( "Error processing " + this.getClass ().getName () + ex.toString () );
            return new ErrorDispatcher( ex );
        }
        return this;
    }

	/* (non-Javadoc)
	 * @see progetto.presentation.dispatcher.RequestDispatcherInt#forward(java.util.Properties, java.util.Properties)
	 */
	public void forward( Object request ) throws Exception {
		TableAppoggi.getInstance().refreshView();
		TableCarichi.getInstance().refreshView();
	}
}
