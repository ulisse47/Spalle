package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.actions.SaveAction;
import java.io.IOException;
import java.util.Properties;

import progetto.model.bean.Verticale;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.ComboCorrentePanel;
import progetto.presentation.view.panel.PalificataOutputDataPanel;
import progetto.presentation.view.panel.StratiTerrenoFondazioniView;
import progetto.presentation.view.panel.VerticaleIndagataCorrentePanel;
import progetto.presentation.view.table.TablePali;
import progetto.presentation.view.table.TableTerreni;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class SetVerticaleCorrenteCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate =
            SpalleBusinessDelegateImpl.getInstance();

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Verticale corrente impostata";
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
            VerticaleIndagataCorrentePanel panel = VerticaleIndagataCorrentePanel.getInstance();
            bDelegate.setVerticaleCorrente((Verticale) panel.getList().getSelectedValue());
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
        TableTerreni.getInstance().refreshView();
        VerticaleIndagataCorrentePanel.getInstance().refreshView();
        StratiTerrenoFondazioniView.getInstance().repaint();
    }
}
