package progetto.presentation.commands;


import java.io.IOException;
import java.util.Properties;

import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.PalificataOutputDataPanel;
import progetto.presentation.view.panel.StratiTerrenoFondazioniView;
import progetto.presentation.view.table.TableM1;
import progetto.presentation.view.table.TableM2;
import progetto.presentation.view.table.TableMAppoggi;
import progetto.presentation.view.table.TableOutputForzeLaterali;
import progetto.presentation.view.table.TablePali;
import progetto.presentation.view.table.TableRisultatiPortanza;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class ElaboraCommand implements Command, RequestDispatcherInt  {


    private SpalleBusinessDelegate bDelegate = 
    	SpalleBusinessDelegateImpl.getInstance();

    public ElaboraCommand() { }

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage ( Properties properties ) {
        return "Elaborazione effettuata";
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
            new SalvaFileCommand().execute( null );
            bDelegate.elabora();
            
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
		TableM1.getInstance().refreshView();
		TableM2.getInstance().refreshView();
		TableMAppoggi.getInstance().refreshView();
		
		PalificataOutputDataPanel.getInstance().refreshView();
		TablePali.getInstance().refreshView();
                TableRisultatiPortanza.getInstance().refreshView();
                TableOutputForzeLaterali.getInstance().refreshView();
                StratiTerrenoFondazioniView.getInstance().repaint();
                
                
	}
}
