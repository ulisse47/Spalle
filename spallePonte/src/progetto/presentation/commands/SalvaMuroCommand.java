package progetto.presentation.commands;


import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.parser.DefaultDataParser;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.components.AbstractBaseTableModel;
import progetto.presentation.view.panel.SpallaInputDataPanel;
import progetto.presentation.view.table.TableAppoggi;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class SalvaMuroCommand implements Command, RequestDispatcherInt  {


    private SpalleBusinessDelegate bDelegate =
    	SpalleBusinessDelegateImpl.getInstance();

    public SalvaMuroCommand () { }

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage ( Properties properties ) {
        return "Archivio Creato";//properties.getProperty("id") + "is found";
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
        	
        	SpallaInputDataPanel inputPanel = SpallaInputDataPanel.getInstance();
        	Hashtable inputs = inputPanel.getInputData();        	
        	AbstractBaseTableModel model = ( AbstractBaseTableModel )TableAppoggi.getInstance().getModel();
        	       	
        	
        	//qui devo fare il parsing dei dati
        	DefaultDataParser parser = new DefaultDataParser();
        	parser.parseData( inputs );
        	parser.parseData( model );        	
                        
        	 //componente businness
            bDelegate.salvaInputSpalla( inputs );
            bDelegate.salvaAppoggi( model.getRowData() );
                              
       } catch ( Exception ex ) {
            System.out.println( "Error processing " + this.getClass ().getName () + ex.toString () );
            throw ex;
        }
        return this;
    }

	/* (non-Javadoc)
	 * @see progetto.presentation.dispatcher.RequestDispatcherInt#forward(java.util.Properties, java.util.Properties)
	 */
	public void forward( Object request ) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
