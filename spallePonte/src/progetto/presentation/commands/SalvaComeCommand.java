package progetto.presentation.commands;

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
import progetto.presentation.view.panel.FondazioneContainerPanel;
import progetto.presentation.view.panel.SpallaContainerPanel;
import progetto.presentation.view.panel.VerificaPortanzaContainerPanel;
import progetto.util.FileChooser;

/**
 * Created by IntelliJ IDEA. User: Andrea Date: 8-dic-2003 Time: 10.34.08 To
 * change this template use Options | File Templates.
 */
public class SalvaComeCommand implements Command, RequestDispatcherInt {

	private SpalleBusinessDelegate bDelegate = SpalleBusinessDelegateImpl.getInstance();

	public SalvaComeCommand() {
	}

	/**
	 * 
	 * @param properties
	 * @return
	 */
	public String getDisplayMessage(Properties properties) {
		return "Archivio Creato";//properties.getProperty("id") + "is found";
	}

	/**
	 * 
	 * @param helper
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public synchronized RequestDispatcherInt execute(RequestHelper helper)
			throws Exception, IOException {

		String cartellaCorrento = bDelegate.getFileCorrente().toString();
                String fileName = null;
		File fileCorrente = null;

		try {
        		saveAs();
			
		} catch (Exception ex) {
			System.out.println("Error processing " + this.getClass().getName()
					+ ex.toString());
			return new ErrorDispatcher(ex);
		}
		return this;
	}
        
        
        public boolean saveAs() throws Exception {
            
            File fileCorrente = bDelegate.getFileCorrente();
            SalvaFileCommand com = new SalvaFileCommand();
            
            //se comunque non esiste il file
            if (fileCorrente == null || !fileCorrente.exists()) {
                return com.save();
            }
            //se esiste già provo a salvare prima
            else {
                if ( com.save() ){
                    bDelegate.setFileCorrente( null );
                    return  com.save();
                } else {
                    //se non riesco a salvare
                    return false;
                }
            }
            
        }

	/*
	 * (non-Javadoc)
	 * 
	 * @see progetto.presentation.dispatcher.RequestDispatcherInt#forward(java.util.Properties,
	 *      java.util.Properties)
	 */
	public void forward(Object request) throws Exception {
		SpallaContainerPanel.getInstance().refreshView();
		FondazioneContainerPanel.getInstance().refreshView();
                VerificaPortanzaContainerPanel.getInstance().refreshView();
	}
}