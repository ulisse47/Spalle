/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import it.ccprogetti.spalleponte.netbeans.actions.AddCaricoAction;
import it.ccprogetti.spalleponte.netbeans.actions.AddCombinazioneAction;
import it.ccprogetti.spalleponte.netbeans.actions.DeleteCaricoAction;
import it.ccprogetti.spalleponte.netbeans.actions.DeleteCombinazioneAction;
import java.awt.BorderLayout;

import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.SwingUtilities;
import org.openide.util.actions.SystemAction;
import progetto.presentation.commands.DeleteCombinazioneCommand;
import progetto.presentation.view.table.TableCombinazioni;

/**
 * @author Andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CombinazioniPanel extends JPanel {

    private JScrollPane tableCombinazioniScrollPane;

    public TableCombinazioni getTabCombo() {
        return tabCombo;
    }

    public void setTabCombo(TableCombinazioni tabCombo) {
        this.tabCombo = tabCombo;
    }

    public JScrollPane getTableCombinazioniScrollPane() {
        return tableCombinazioniScrollPane;
    }

    public void setTableCombinazioniScrollPane(JScrollPane tableCombinazioniScrollPane) {
        this.tableCombinazioniScrollPane = tableCombinazioniScrollPane;
    }

    /**
     * 
     */
    public CombinazioniPanel() {
        super();
        Button bUp = new Button();
        Button bDown = new Button();
        bUp.setSize(20, 60);
        bUp.setLabel("SU");
        bDown.setSize(20, 60);
        bDown.setLabel("GIU");
        JPanel jb = new JPanel(new BorderLayout());
        jb.add(bUp, BorderLayout.NORTH);
        jb.add(bDown, BorderLayout.SOUTH);


        setLayout(new BorderLayout());
        tableCombinazioniScrollPane = new JScrollPane();
        tableCombinazioniScrollPane.getViewport().add(tabCombo);
//        tableCombinazioniScrollPane.getViewport().add(jb,BorderLayout.WEST);

        add(tableCombinazioniScrollPane, BorderLayout.CENTER);

    }

    TableCombinazioni tabCombo = TableCombinazioni.getInstance();

    
    }

