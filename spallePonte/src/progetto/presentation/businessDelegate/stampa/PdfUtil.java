/*
 * Created on 24-mar-2004
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */

package progetto.presentation.businessDelegate.stampa;


import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Vector;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author a_cavalieri
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class PdfUtil {

    public static final int PERCENTAGE = 0;
    public static final int INTEGER = 1;
    public static final int TEXT = 2;
    public static final int EURO = 3;
    public static final int DATE_ITALY = 4;    
    public static final int EMPTY = 5;

    public static String EMPTY_EURO = ",00";

    public static String EMPTY_INTEGER = "";

    private static final Paragraph newLine = new Paragraph("\n");

    public static void writeSpace(Document doc, int height)
            throws DocumentException {

        PdfPTable datatable = new PdfPTable(1);
        //float headerwidths2[] = { 1 }; // percentage
        //datatable.setWidths( headerwidths2 );
        datatable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        datatable.getDefaultCell().setFixedHeight(height);
        datatable.addCell("");
        doc.add(datatable);
    }

    /**
     * @param number
     * @param f
     * @return
     */
    public static String format(String value, int data_type) {

        switch (data_type) {
        case PERCENTAGE:
            return formatToPercent(value);
        case EURO:
            return formatToEuro(value);
        case INTEGER:
            return formatToInteger(value);
        case TEXT:
            return value;
        case DATE_ITALY:
            //return DateTimeFormat.toItDate(value);
        case EMPTY:
	        return "";
    	}

        return value;
    }

    

    /**
     * @param number
     * @return
     */
    public static String formatToInteger(String number) {

        String result;

        try {
            int n = Integer.parseInt(number);
            result = String.valueOf(n);
        } catch (Exception ex) {
            result = EMPTY_INTEGER;
        }
        return result;
    }

    /**
     * @param number
     * @return
     */
    public static String formatToEuro(String number) {
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        nf.setMinimumIntegerDigits(1);

        String result;

        try {
            double n = Double.parseDouble(number);
            result = nf.format(n);
        } catch (Exception ex) {
            result = EMPTY_EURO;
        }
        return result;
    }

    /**
     * @param number
     * @return
     */
    public static String formatToPercent(String number) {

        String value = formatToEuro(number);

        if (value.equals(EMPTY_EURO)) {

        return "%";

        }

        return value.concat("%");
    }

    public static void writeNewLine(Document doc) throws DocumentException {
        doc.add(newLine);
    }

    /**
     *  
     */
    public static PdfPTable getTableBordered(PdfPTable table) {

        PdfPTable datatable = new PdfPTable(1);
        datatable.setHorizontalAlignment(Element.ALIGN_LEFT);
        datatable.setWidthPercentage(100); // percentage

        datatable.getDefaultCell().setVerticalAlignment(Element.ALIGN_LEFT);
        datatable.getDefaultCell().setBorder(Rectangle.BOX);
        datatable.addCell(table);

        return datatable;

    }

    /**
     * @param text
     * @param f
     * @param table
     */
    public static void writeText(String text, Font f, PdfPTable table, int align) {

        PdfPTable t = new PdfPTable(1);
        t.setHorizontalAlignment(Element.ALIGN_LEFT);
        t.setWidthPercentage(100); // percentage

        //testo
        t.getDefaultCell().setHorizontalAlignment(align);
        t.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        t.addCell(new Phrase(text, f));

        table.addCell(t);
    }

    /**
     * @param cb
     * @param deltaX
     * @param deltaY
     * @param x
     * @param y
     */
    public static void drawCheckBox(PdfWriter writer, float deltaX,
            float deltaY, float x, float y) {

        PdfContentByte cb = writer.getDirectContent();

        cb.setLineWidth(0.7f);
        cb.moveTo(x, y);
        cb.lineTo(x + deltaX, y + deltaY);
        cb.stroke();
        cb.moveTo(x, y + deltaY);
        cb.lineTo(x + deltaX, y);
        cb.stroke();
    }
    
    /**
     * @deprecated Dovrebbe fare una deep copy della Table ma non funziona
     * @param table
     * @return
     */
    public static DataTable cloneTable( DataTable table ){
    	
        
    	DataTable copy = new DataTable();
        Vector vec;
        String obj;
        
        for ( int i = 1; i <= table.getRows(); i++ ){
            copy.addRow();
        	vec = ( Vector )table.getVector( i );
        	copy.addRow(  cloneVector( vec ) );            
        	
        }
        
        return copy;        
    }
    
    
    /**
     * 
     * @param original
     * @return
     */
    private static Vector cloneVector( Vector original ){
    	Vector copy = new Vector() ;
    	String obj = "";
    	
    	Iterator iter = original.iterator();
    	while ( iter.hasNext() ){
    		obj = ( String )iter.next();
    		copy.add(  new String( obj ) );
    	}    	
    	
    	return copy;
    }
    
    
    
}



