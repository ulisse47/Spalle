package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.view.CarichiViewAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import progetto.model.bean.Combinazione;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.ComboCorrentePanel;
import progetto.presentation.view.panel.PalificataOutputDataPanel;
import progetto.presentation.view.panel.VerticaleIndagataCorrentePanel;
import progetto.presentation.view.table.TableCombinazioni;

/**
 * Created by IntelliJ IDEA. User: Andrea Date: 8-dic-2003 Time: 10.34.08 To
 * change this template use Options | File Templates.
 */
public class DeleteCombinazioneCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate = SpalleBusinessDelegateImpl.getInstance();

    public DeleteCombinazioneCommand() {
    }

    /**
     * 
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Eliminata Combinazione";
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
            bDelegate.salvaInLocale();

            new CarichiViewAction().actionPerformed(new ActionEvent(new DeleteCombinazioneCommand(), 0, "delete combinazione"));

            ArrayList listaCombinazioni = bDelegate.loadCombinazioniFromModel();
            int n = listaCombinazioni.size();

            if (n == 0) {
                JOptionPane.showMessageDialog(null, "nessuna combinazione è stata salvata!",
                        "Elimina Combinazione", JOptionPane.INFORMATION_MESSAGE);


            } else {
                Object[] options = new Object[n];
                for (int i = 0; i < n; i++) {
                    options[i] = listaCombinazioni.get(i);
                }

                Object returnValue = JOptionPane.showInputDialog(null,
                        "numero combinazione", "Elimina Combinazione",
                        JOptionPane.QUESTION_MESSAGE, null, options,
                        new Double(0));
                if (returnValue != null) {
                    //componente businness
                    Combinazione c = (Combinazione) returnValue;
                    bDelegate.deleteCombinazione(c);
                }
            }

        } catch (Exception ex) {
            System.out.println("Error processing " + this.getClass().getName() + ex.toString());
            return new ErrorDispatcher(ex);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progetto.presentation.dispatcher.RequestDispatcherInt#forward(java.util.Properties,
     *      java.util.Properties)
     */
    public void forward(Object request) throws Exception {
        TableCombinazioni.getInstance().refreshView();
        ComboCorrentePanel.getInstance().refreshView();
        PalificataOutputDataPanel.getInstance().refreshView();
        VerticaleIndagataCorrentePanel.getInstance().refreshView();
    }
}
