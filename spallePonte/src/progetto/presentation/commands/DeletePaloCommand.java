package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.view.FondazioniViewAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.table.TablePali;

/**
 * Created by IntelliJ IDEA. User: Andrea Date: 8-dic-2003 Time: 10.34.08 To
 * change this template use Options | File Templates.
 */
public class DeletePaloCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate = SpalleBusinessDelegateImpl.getInstance();

    /**
     * 
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Rimosso Palo";
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

            new FondazioniViewAction().actionPerformed(new ActionEvent(new DeletePaloCommand(), 0, "Delete palo"));

            ArrayList listaPali = bDelegate.loadPalificataFromModel();
            int n = listaPali.size();

            if (n == 0) {
                JOptionPane.showMessageDialog(null, "nessun palo è stato salvato!",
                        "Elimina Palo", JOptionPane.INFORMATION_MESSAGE);


            } else {
                Integer[] options = new Integer[n];
                for (int i = 0; i < n; i++) {
                    options[i] = new Integer(i);
                }

                Object returnValue = JOptionPane.showInputDialog(null,
                        "numero palo", "Elimina Palo",
                        JOptionPane.QUESTION_MESSAGE, null, options,
                        new Double(0));
                if (returnValue != null) {
                    //componente businness
                    int id_combo = ((Integer) returnValue).intValue();
                    bDelegate.deletePalo(id_combo);
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
        TablePali.getInstance().refreshView();
    }
}
