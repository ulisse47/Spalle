package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.view.PortanzaViewAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;
import progetto.model.bean.Verticale;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.StratiTerrenoFondazioniView;
import progetto.presentation.view.panel.VerticaleIndagataCorrentePanel;
import progetto.presentation.view.table.TableTerreni;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class RemoveVerticaleIndagataCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate =
            SpalleBusinessDelegateImpl.getInstance();

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Eliminata Verticale Indagata";
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

            new PortanzaViewAction().actionPerformed(new ActionEvent(new RemoveVerticaleIndagataCommand(), 0, "remove verticale"));

            ArrayList verticali = bDelegate.loadVerticaliFromModel();
            int n = verticali.size();

            if (n == 0) {
                JOptionPane.showMessageDialog(null, "nessuna verticale è stata salvata!",
                        "Elimina Verticale", JOptionPane.INFORMATION_MESSAGE);

            } else {
                Object[] options = new Object[n];
                for (int i = 0; i < n; i++) {
                    options[i] = verticali.get(i);
                }

                Object returnValue = JOptionPane.showInputDialog(null,
                        "numero verticale", "Elimina Verticale",
                        JOptionPane.QUESTION_MESSAGE, null, options,
                        new Double(0));
                if (returnValue != null) {
                    //componente businness
                    Verticale id_combo = (Verticale) returnValue;
                    bDelegate.deleteVerticaleIndagata(id_combo);
                }
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
        TableTerreni.getInstance().refreshView();
        VerticaleIndagataCorrentePanel.getInstance().refreshView();
        StratiTerrenoFondazioniView.getInstance().repaint();
    }
}
