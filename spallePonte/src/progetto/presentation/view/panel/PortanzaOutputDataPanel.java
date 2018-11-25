/*
 * Created on 28-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.panel;

import javax.swing.JPanel;
import javax.swing.JTextField;

import progetto.presentation.businessDelegate.SpalleBusinessDelegate;
import progetto.presentation.businessDelegate.SpalleBusinessDelegateImpl;
import progetto.presentation.util.MyBeanUtils;
import progetto.presentation.view.components.AbstractInputPanel;
import progetto.presentation.view.components.DefaultDoubleField;
import progetto.presentation.view.components.FormattedDoubleField;
import progetto.presentation.view.util.InputComponent;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PortanzaOutputDataPanel extends AbstractInputPanel {

	private static PortanzaOutputDataPanel panel;

	public static synchronized PortanzaOutputDataPanel getInstance() {
		if (panel == null) {
			panel = new PortanzaOutputDataPanel();
		}
		return panel;
	}

	protected void init() {

		setXLabel( 250 );
		int width = 100;
		JPanel pPesi = addInputPanel("Palificata", 0, 0);
		addInputField( pPesi ,new DefaultDoubleField(), 0,0, "kpali","k = Mp/Vp (m)" );

	//	addInputField(pPesi, new FormattedDoubleField( 4, true, width ), 0, 0, "kpali", "k = Mp/Vp (m)");
		addOutputField(pPesi, new FormattedDoubleField( 0, false,width ), 1, 0, "Mp", "Mp(kNm)");
		addOutputField(pPesi, new FormattedDoubleField( 0, false, width ), 2, 0, "Vp", "Vp(kN)");

		JPanel pTerrenoCLS = addInputPanel("sollecitazioni", 2, 0);
//		addInputField(pTerrenoCLS, new DefaultDoubleField(width), 0, 0, "x_verifica", "coordinata x sezione di verifica(cm)");
//		addInputField(pTerrenoCLS, new DefaultDoubleField(width), 1, 0, "y_verifica", "coordinata y sezione di verifica(cm)");		
		addOutputField(pTerrenoCLS, new FormattedDoubleField( 0, false, width ), 0, 0, "Mxmonte", "Mx monte(kNm)");
		addOutputField(pTerrenoCLS, new FormattedDoubleField( 0, false, width ), 1, 0, "Vmonte", "V monte(kNm)");
		addOutputField(pTerrenoCLS, new FormattedDoubleField( 0, false, width ), 2, 0, "mxmonte", "mx monte(kNm/ml)");
		addOutputField(pTerrenoCLS, new FormattedDoubleField( 0, false, width ), 3, 0, "vmonte", "v monte(kN/ml)");

		addOutputField(pTerrenoCLS, new FormattedDoubleField( 0, false, width ), 4, 0, "Mxvalle", "Mx valle(kNm)");
		addOutputField(pTerrenoCLS, new FormattedDoubleField( 0, false, width ), 5, 0, "Vvalle", "V valle(kNm)");
		addOutputField(pTerrenoCLS, new FormattedDoubleField( 0, false, width ), 6, 0, "mxvalle", "mx valle(kNm/ml)");
		addOutputField(pTerrenoCLS, new FormattedDoubleField( 0, false, width ), 7, 0, "vvalle", "v valle(kN/ml)");
		
		
	}

	/**
	 *  
	 */
	private PortanzaOutputDataPanel() {
		super();
	}


	
	/*
	 * (non-Javadoc)
	 * 
	 * @see progetto.presentation.view.util.ViewComponent#refreshModel()
	 */
	public void refreshView() {
		SpalleBusinessDelegate del = SpalleBusinessDelegateImpl.getInstance();
		try {
			InputComponent comp;
/*			*/
			
			
			
			double[] Mpalo = del.getM_V_SingoloPalo();
			comp = (InputComponent) outputs.get("Mp");
			comp.setValue(new Double(Mpalo[0]));
			comp = (InputComponent) outputs.get("Vp");
			comp.setValue(new Double(Mpalo[1]));
			
			double[][] sigmaM = del.getMverifica();
			comp = (InputComponent) outputs.get("Mxmonte");
			comp.setValue(new Double(sigmaM[0][0]));
			comp = (InputComponent) outputs.get("Vmonte");
			comp.setValue(new Double(sigmaM[1][0]));
			comp = (InputComponent) outputs.get("mxmonte");
			comp.setValue(new Double(sigmaM[0][1]));
			comp = (InputComponent) outputs.get("vmonte");
			comp.setValue(new Double(sigmaM[1][1]));
			
			comp = (InputComponent) outputs.get("Mxvalle");
			comp.setValue(new Double(sigmaM[2][0]));
			comp = (InputComponent) outputs.get("Vvalle");
			comp.setValue(new Double(sigmaM[3][0]));
			comp = (InputComponent) outputs.get("mxvalle");
			comp.setValue(new Double(sigmaM[2][1]));
			comp = (InputComponent) outputs.get("vvalle");
			comp.setValue(new Double(sigmaM[3][1]));
			
			
			
			
			//setta i valori di inputs
			MyBeanUtils.setProperties( del.loadSpallaCorrenteFromModel(), inputs );

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}