package progetto.presentation.commands;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.table.TableStringConverter;
import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.parser.DefaultDataParser;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.components.AbstractBaseTableModel;
import progetto.presentation.view.panel.VerticaleIndagataCorrentePanel;
import progetto.presentation.view.table.TableCarichi;
import progetto.presentation.view.table.TableTerreni;

/**
 * Created by IntelliJ IDEA. User: Andrea Date: 8-dic-2003 Time: 10.34.08 To
 * change this template use Options | File Templates.
 */
public class SalvaVerticaleCorrenteCommand implements Command,
        RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate = SpalleBusinessDelegateImpl.getInstance();
    

    public SalvaVerticaleCorrenteCommand() {
    }

    /**
     * 
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Verticale Corrente Salvata";
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

           AbstractBaseTableModel model = (AbstractBaseTableModel) TableTerreni.getInstance().getModel();
/*           VerticaleIndagataCorrentePanel inputPanel = VerticaleIndagataCorrentePanel.getInstance();
           Hashtable inputs = inputPanel.getInputData();

            //qui devo fare il parsing dei dati
            DefaultDataParser parser = new DefaultDataParser();
            parser.parseData(inputs);
*/
     
            if (model.getRowCount() != 0) {

                //qui devo fare il parsing dei dati
                DefaultDataParser parser = new DefaultDataParser();
                parser.parseData(model);

                //componente businness
                bDelegate.salvaVerticaleCorrente(model.getRowData() );
            }else{
                bDelegate.salvaVerticaleCorrente(new  Double[ 0 ][7] );
            }

        } catch (Exception ex) {
            System.out.println("Error processing " + this.getClass().getName() + ex.toString());
            throw ex;
        //return new ErrorDispatcher(ex);
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
    // TODO Auto-generated method stub

    }
}
