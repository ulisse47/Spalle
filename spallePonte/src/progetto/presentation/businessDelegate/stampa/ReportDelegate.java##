package progetto.presentation.businessDelegate.stampa;

import java.io.File;
import java.util.ArrayList;

import progetto.model.bean.Spalla;
import progetto.model.bean.SpallaManager;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;

public class ReportDelegate {
	
	public void fill() throws JRException{
		
		Spalla spalla = SpallaManager.getInstance().getCurrentSpalla();
		Object[] array = new Object[]{ spalla };
		
		
		JasperPrint jasperPrint = JasperFillManager.fillReport( 
				getClass().getResourceAsStream("/report/spalla.jasper"), null,
				new JRBeanArrayDataSource( array ) );
		
		
		File destFile = new File( jasperPrint.getName() + ".rtf");
		
		ArrayList<JasperPrint> lista = new ArrayList<JasperPrint>();
		lista.add(jasperPrint);
		
		
		JRRtfExporter exporter = new JRRtfExporter();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, lista);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
		exporter.exportReport();
	}

}
