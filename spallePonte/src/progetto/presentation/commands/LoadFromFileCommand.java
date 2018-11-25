package progetto.presentation.commands;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;

import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.dispatcher.ErrorDispatcher;
import progetto.presentation.dispatcher.RequestDispatcherInt;
import progetto.presentation.util.Command;
import progetto.presentation.util.RequestHelper;
import progetto.presentation.view.panel.CaricoCorrentePanel;
import progetto.presentation.view.panel.ComboCorrentePanel;
import progetto.presentation.view.panel.SpallaContainerPanel;
import progetto.presentation.view.panel.SpallaInputDataPanel;
import progetto.presentation.view.panel.PalificataOutputDataPanel;
import progetto.presentation.view.panel.PortanzaOutputDataPanel;
import progetto.presentation.view.panel.StratiTerrenoFondazioniView;
import progetto.presentation.view.panel.VerticaleIndagataCorrentePanel;
import progetto.presentation.view.table.TableAppoggi;
import progetto.presentation.view.table.TableCarichi;
import progetto.presentation.view.table.TableCombinazioni;
import progetto.presentation.view.table.TablePali;
import progetto.presentation.view.table.TableTerreni;

import progetto.util.FileChooser;

/**
 * Created by IntelliJ IDEA. User: Andrea Date: 8-dic-2003 Time: 10.34.08 To
 * change this template use Options | File Templates.
 */
public class LoadFromFileCommand implements Command, RequestDispatcherInt {

    private SpalleBusinessDelegate bDelegate = SpalleBusinessDelegateImpl
            .getInstance();

    public LoadFromFileCommand() {
    }

    /**
     *
     * @param properties
     * @return
     */
    public String getDisplayMessage(Properties properties) {
        return "Carico Corrente Salvato";
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

            FileChooser chooser = new FileChooser(JFileChooser.OPEN_DIALOG,
                    "spa", "SPA");
            try {
                String currentPath = bDelegate.getPathCorrente();
                File currDir = new File(currentPath);
                    chooser.setCurrentDirectory(currDir);
            } catch (Exception e) {
            }

            chooser.setTipo(FileChooser.OPEN);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == FileChooser.APPROVE_OPTION) {

                String fileName = chooser.getAbsoluteFileName();
                //componente businness
                bDelegate.setPathCorrente(new File(fileName).getAbsolutePath());
                bDelegate.openFromFile(fileName);
            }

        } catch (Exception ex) {
            System.out.println("Error processing " + this.getClass().getName()
                    + ex.toString());
            return new ErrorDispatcher(ex);
        }
        return this;
    }

    public void openFile(String file) throws Exception {
        bDelegate.openFromFile(file);
    }

    public void openFromResource(String resource) throws Exception {

        bDelegate.openFromResource(getClass().getResourceAsStream(resource));
    }

    public void openTemplate() throws Exception {
        openFromResource("/progetto/model/template.spa");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see progetto.presentation.dispatcher.RequestDispatcherInt#forward(java.util.Properties,
	 *      java.util.Properties)
     */
    public void forward(Object request) throws Exception {
        TableCarichi.getInstance().refreshView();
        TableAppoggi.getInstance().refreshView();
        CaricoCorrentePanel.getInstance().refreshView();
        ComboCorrentePanel.getInstance().refreshView();
        TableCombinazioni.getInstance().refreshView();
        TablePali.getInstance().refreshView();
        SpallaInputDataPanel.getInstance().refreshView();
        PalificataOutputDataPanel.getInstance().refreshView();
        SpallaContainerPanel.getInstance().refreshView();
        TableTerreni.getInstance().refreshView();
        VerticaleIndagataCorrentePanel.getInstance().refreshView();
        VerticaleIndagataCorrentePanel.getInstance().refreshView();
        StratiTerrenoFondazioniView.getInstance().repaint();
        PortanzaOutputDataPanel.getInstance().repaint();

        //CarpenteriaSpallaView.getInstance();
        //SezioneSpallaView.getInstance(): 
    }
}
