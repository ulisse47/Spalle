package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.view.CarichiViewAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.ComboCorrentePanel;
import progetto.presentation.view.table.TableCombinazioni;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class AddCombinazioneCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate =
            SpalleBusinessDelegateImpl.getInstance();

    public AddCombinazioneCommand() {
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

            new CarichiViewAction().actionPerformed(new ActionEvent(new AddCombinazioneCommand(), 0, "Add combo"));

            //componente businness
            Object returnValue = JOptionPane.showInputDialog(null,
                    "nome della combinazione", "Aggiungi Combinazione",
                    JOptionPane.QUESTION_MESSAGE, null, null, "nuova combo");
            if (returnValue != null) {
                String nome = (String) returnValue;
                bDelegate.addCombinazione(nome);
            }



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
        TableCombinazioni.getInstance().refreshView();
        ComboCorrentePanel.getInstance().refreshView();
    }
}
