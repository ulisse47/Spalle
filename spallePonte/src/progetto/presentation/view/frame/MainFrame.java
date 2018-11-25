/*
 * Created on 2-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.frame;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.commands.SalvaFileCommand;
import progetto.presentation.view.panel.MainContainerPanel;

public class MainFrame extends JFrame {
	JPanel contentPane;

	BorderLayout borderLayout1 = new BorderLayout();

	//Construct the frame
	public MainFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		this.setSize(new Dimension(800, 600));
		this.setTitle("Frame Title");
		contentPane.add(new MainContainerPanel());
	}

	//Overridden so we can exit when window is closed
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		int option;
		SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			option = JOptionPane.showConfirmDialog(null,
					"vuoi salvare il progetto?", "Message", 1);
			if (option == JOptionPane.YES_OPTION) {
				try { 
					new SalvaFileCommand().execute( null );
					System.exit(0);
				}catch ( Exception ex ){
					ex.printStackTrace();
					System.exit(0);
				}
			}
			 else if (option == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		}

	}
}