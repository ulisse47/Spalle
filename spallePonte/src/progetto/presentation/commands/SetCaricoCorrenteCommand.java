package progetto.presentation.commands;

import java.io.IOException;
import java.util.Properties;

import progetto.model.bean.Carico;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.CaricoCorrentePanel;
import progetto.presentation.view.table.TableCarichi;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class SetCaricoCorrenteCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate =
            SpalleBusinessDelegateImpl.getInstance();

    public SetCaricoCorrenteCommand() {
    }

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Carico corrente impostato";
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

            //componente businness
            new SalvaCaricoCorrenteCommand().execute(null);
            CaricoCorrentePanel panel = CaricoCorrentePanel.getInstance();
//            bDelegate.setCaricoCorrente((Carico) panel.getComboCarico().getSelectedItem());
                bDelegate.setCaricoCorrente((Carico) panel.getList().getSelectedValue());


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
        TableCarichi.getInstance().refreshView();
        CaricoCorrentePanel.getInstance().refreshView();
    }
}
