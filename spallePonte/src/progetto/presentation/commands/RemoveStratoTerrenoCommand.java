package progetto.presentation.commands;

import it.ccprogetti.spalleponte.netbeans.view.PortanzaViewAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;
import progetto.model.bean.SpallaManager;
import progetto.model.bean.Verticale;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.table.TableTerreni;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 8-dic-2003
 * Time: 10.34.08
 * To change this template use Options | File Templates.
 */
public class RemoveStratoTerrenoCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate =
            SpalleBusinessDelegateImpl.getInstance();

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Rimosso Starto Terreno";
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

            new PortanzaViewAction().actionPerformed(new ActionEvent(new RemoveStratoTerrenoCommand(), 0, "remove strato"));

            SpallaManager man = SpallaManager.getInstance();
            Verticale curVe = man.getCurrentVerticale();

            if (curVe == null) {
                JOptionPane.showMessageDialog(null, "nessun verticale indagata definitia",
                        "Elimina strato terreno", JOptionPane.INFORMATION_MESSAGE);

            } else {
                ArrayList listaTerreni = curVe.getStrati();

                int n = listaTerreni.size();

                if (n == 0) {
                    JOptionPane.showMessageDialog(null, "nessun terreno è stato salvato!",
                            "Elimina strato terreno", JOptionPane.INFORMATION_MESSAGE);

                } else if (n == 1) {
                    JOptionPane.showMessageDialog(null, "minimo uno starto di terreno!",
                            "Elimina strato di terreno", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    Integer[] options = new Integer[n];
                    for (int i = 0; i < n; i++) {
                        options[i] = new Integer(i);
                    }

                    Object returnValue = JOptionPane.showInputDialog(null,
                            "numero strato", "Elimina strato terreno",
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            new Double(0));
                    if (returnValue != null) {
                        //componente businness
                        int id_combo = ((Integer) returnValue).intValue();
                        bDelegate.removeStratoTerreno(id_combo);
                    }
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
    }
}
