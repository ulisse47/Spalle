package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.view.FondazioniViewAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;

import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.FondazioniView;
import progetto.presentation.view.table.TablePali;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class EliminaTuttiPaliCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate =
            SpalleBusinessDelegateImpl.getInstance();

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Elimina Tutti i Pali";
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

            new FondazioniViewAction().actionPerformed(new ActionEvent(new EliminaTuttiPaliCommand(), 0, "Elimina pali"));

            bDelegate.eliminaTuttiPali();


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
        FondazioniView.getInstance().repaint();
        TablePali.getInstance().refreshView();

    }
}
