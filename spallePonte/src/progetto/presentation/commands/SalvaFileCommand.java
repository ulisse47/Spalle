package progetto.presentation.commands;

import it.ccprogetti.activation.core.StartUp;
import it.ccprogetti.activation.core.StartUpExt;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;
import progetto.model.bean.SpallaManager;

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
public class SalvaFileCommand implements Command, RequestDispatcherInt {

	private SpalleBusinessDelegate bDelegate = SpalleBusinessDelegateImpl.getInstance();

	public SalvaFileCommand() {
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

		String fileName = null;
		File fileCorrente = null;

		try {

			save();
			
		} catch (Exception ex) {
			System.out.println("Error processing " + this.getClass().getName()
					+ ex.toString());
			return new ErrorDispatcher(ex);
		}
		return this;
	}
        
        
        public boolean save() throws Exception {
            
            String fileName = null;
            File fileCorrente = null;
            
            fileCorrente = bDelegate.getFileCorrente();
            
            new SalvaMuroCommand().execute( null );
            new SalvaCaricoCorrenteCommand().execute( null );
            new SalvaComboCommand().execute( null );
            new SalvaPalificataCommand().execute( null );
            new SalvaFondazioniCommand().execute( null );
            new SalvaVerticaleCorrenteCommand().execute(null);
            
            if ( bDelegate.getMode() == StartUpExt.DEMO ){
                return true;
            }
            
            
            //caso file nuovo
            if (fileCorrente == null || !fileCorrente.exists() ) {
                //scegli file
                FileChooser chooser = new FileChooser(JFileChooser.SAVE_DIALOG,
                        "spa", "SPA");
                
                try{
                    String curPath = bDelegate.getPathCorrente();
                    File dir = new File(curPath);
                    chooser.setCurrentDirectory(dir);
                }
                catch(Exception ex){
                
                }
                
                int returnVal = chooser.showSaveDialog(null);
                
                if (returnVal == FileChooser.APPROVE_OPTION) {
                    fileName = chooser.getAbsoluteFileName();
                    fileCorrente = new File(fileName);
                    bDelegate.saveToFile(fileName);
                    bDelegate.setPathCorrente(fileCorrente.getAbsolutePath());
                    return true;
                }
                if (returnVal == FileChooser.CANCEL_OPTION) {
                    return false;
                }
            } else {
                fileName = fileCorrente.getAbsolutePath();
                bDelegate.saveToFile(fileName);
                return true;
            }
            
            return false;
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