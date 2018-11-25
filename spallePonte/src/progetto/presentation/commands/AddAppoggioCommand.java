package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.view.CarichiViewAction;
import it.ccprogetti.spalleponte.netbeans.view.SpallaViewAction;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;
import org.openide.windows.TopComponent;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.SpallaContainerPanel;
import progetto.presentation.view.table.TableAppoggi;
import progetto.presentation.view.table.TableCarichi;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class AddAppoggioCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate =
            SpalleBusinessDelegateImpl.getInstance();

    public AddAppoggioCommand() {
    }

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Aggiunto Appoggio";
    }

    /**
     *
     * @param helper
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public synchronized RequestDispatcherInt execute(RequestHelper helper) throws Exception,
            IOException {
        try {
            bDelegate.salvaInLocale();
            
            new SpallaViewAction().actionPerformed( new ActionEvent( new AddAppoggioCommand(), 0, "Add appoggio")); 

            bDelegate.addAppoggio();
            JOptionPane.showMessageDialog(null, "Aggiunto Appoggio",
                    "Aggiungi appoggio", JOptionPane.INFORMATION_MESSAGE);
      
        } catch (Exception ex) {
            System.out.println("Error processing " + this.getClass().getName() + ex.toString());
            return new ErrorDispatcher(ex);
        }
        return this;
    }

    /* (non-Javadoc)
     * @see progetto.presentation.dispatcher.RequestDispatcherInt#forward(java.util.Properties, java.util.Properties)
     */
    public void forward(Object request) throws Exception {
        TableAppoggi.getInstance().refreshView();
        TableCarichi.getInstance().refreshView();
    }
}
