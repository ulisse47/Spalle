package progetto.presentation.commands;

import java.io.IOException;
import java.util.Properties;

import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.CaricoCorrentePanel;
import progetto.presentation.view.panel.ComboCorrentePanel;
import progetto.presentation.view.panel.FondazioneContainerPanel;
import progetto.presentation.view.panel.SpallaContainerPanel;
import progetto.presentation.view.panel.SpallaInputDataPanel;
import progetto.presentation.view.panel.PalificataOutputDataPanel;
import progetto.presentation.view.panel.PortanzaOutputDataPanel;
import progetto.presentation.view.panel.VerificaPortanzaContainerPanel;
import progetto.presentation.view.panel.VerticaleIndagataCorrentePanel;
import progetto.presentation.view.table.TableAppoggi;
import progetto.presentation.view.table.TableCarichi;
import progetto.presentation.view.table.TableCombinazioni;
import progetto.presentation.view.table.TablePali;

/**
 * Created by IntelliJ IDEA. User: Andrea Date: 8-dic-2003 Time: 10.34.08 To
 * change this template use Options | File Templates.
 */
public class NewProgettoCommand implements Command, RequestDispatcherInt {

	private SpalleBusinessDelegate bDelegate = SpalleBusinessDelegateImpl.getInstance();

	public NewProgettoCommand() {
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

		try {
                    
                    nuovoProgetto();
                    
		} catch (Exception ex) {
			System.out.println("Error processing " + this.getClass().getName()
					+ ex.toString());
			return new ErrorDispatcher(ex);
		}
		return this;
	}
        
        public boolean nuovoProgetto() throws Exception {            
            
            if ( new CloseCommand().close() ){
                new LoadFromFileCommand().openTemplate();
                return true;                
            }
            
            //annula nuovo progetto
            else {
                return false;
            }            
                 
        }

	/*
	 * (non-Javadoc)
	 * 
	 * @see progetto.presentation.dispatcher.RequestDispatcherInt#forward(java.util.Properties,
	 *      java.util.Properties)
	 */
	public void forward(Object request) throws Exception {
                
                FondazioneContainerPanel.getInstance().refreshView();
                VerificaPortanzaContainerPanel.getInstance().refreshView();
                TableCarichi.getInstance().refreshView();
		TableAppoggi.getInstance().refreshView();
		CaricoCorrentePanel.getInstance().refreshView();
		ComboCorrentePanel.getInstance().refreshView();
		TableCombinazioni.getInstance().refreshView();
		TablePali.getInstance().refreshView();
		SpallaInputDataPanel.getInstance().refreshView();
		PalificataOutputDataPanel.getInstance().refreshView();
                SpallaContainerPanel.getInstance().refreshView();
                VerticaleIndagataCorrentePanel.getInstance().refreshView();
                PortanzaOutputDataPanel.getInstance().refreshView();
	}
}