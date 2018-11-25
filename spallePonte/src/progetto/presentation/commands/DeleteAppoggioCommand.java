package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.view.SpallaViewAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import progetto.model.bean.Appoggio;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.table.TableAppoggi;
import progetto.presentation.view.table.TableCarichi;

/**
 * Created by IntelliJ IDEA. User: Andrea Date: 8-dic-2003 Time: 10.34.08 To
 * change this template use Options | File Templates.
 */
public class DeleteAppoggioCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate = SpalleBusinessDelegateImpl.getInstance();

    public DeleteAppoggioCommand() {
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
    public synchronized RequestDispatcherInt execute(RequestHelper helper)
            throws Exception, IOException {
        try {

            bDelegate.salvaInLocale();

            new SpallaViewAction().actionPerformed(new ActionEvent(new DeleteAppoggioCommand(), 0, "Delete appoggio"));

            //int n = TableAppoggi.getInstance().getRowCount();
            ArrayList appoggi = bDelegate.loadAppoggiFromModel();
            int n = appoggi.size();

            if (n == 0) {
                JOptionPane.showMessageDialog(null, "nessun appoggio è stato salvato!",
                        "Elimina Appoggio", JOptionPane.INFORMATION_MESSAGE);


            } else {
                Object[] options = new Object[n];
                for (int i = 0; i < n; i++) {
                    options[i] = appoggi.get(i);
                }

                Object returnValue = JOptionPane.showInputDialog(null,
                        "numero appoggio", "Elimina Appoggio",
                        JOptionPane.QUESTION_MESSAGE, null, options,
                        new Double(0));
                if (returnValue != null) {
                    //componente businness
                    Appoggio id_appoggio = (Appoggio) returnValue;
                    bDelegate.deleteAppoggio(id_appoggio);
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
        TableAppoggi.getInstance().refreshView();
        TableCarichi.getInstance().refreshView();
    }
}
