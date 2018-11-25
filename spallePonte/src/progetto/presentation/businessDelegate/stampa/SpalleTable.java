/*
 * Created on 8-apr-2004
 *  
 */

package progetto.presentation.businessDelegate.stampa;

import java.awt.Color;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;




/**
 * @author a_cavalieri
 *  
 */
public class SpalleTable {

    /** una volta letto un record lo cancella dalla tabella */
    public static int FORWARD_ONLY = 1;
    
    /** legge i record senza cancellarli dalla tabella */
    public static int FORWARD_BACKWARD = 0;
    
    private int reading_type = FORWARD_ONLY;
    
    public boolean show_headers = true;

    //private float fixed_height = DEFAULT_HEIGHT;

    /** * numero di righe fisse della tabella anche se vuota */
    private int rows = 4;

    private int writtenRows;
  
    private int[] widths;

    private float[] widths_float;

    private int[] cell_alignment;

    private int cell_alignment_common;
    
    private int[] header_alignment;
    
    private Font[] column_fonts;
    
    /** tipo di dato contenuto nella colonna */
    private int[] column_data_type;
    
    private int column_data_type_common;
    
    
    /** Font di tutte le celle */
    private Font fontCell;

    /** Font degli header della tabella */
    private Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 9);

    /** Colore dello sfondo della riga degli header */
    public Color header_color = Color.LIGHT_GRAY;

    public static Color CELLS_COLOR = Color.WHITE;

    public static float DEFAULT_HEIGHT = 15;

    private Table datatable;

    private String[] headers;

    private int n_col;

    private int currentRow = 0;

    /** contiene l'importo dei redditi per tipo */
    private DataTable table;

    /**
     * @return
     */
    public Table createTable() throws DocumentException {
        createTable(n_col);
        return datatable;
    }

    /**
     * @param table
     * @param fontCell
     */
    public SpalleTable( DataTable table, Font f) {

        this.fontCell = f;
        this.table = table;
        this.n_col = table.getCol();
        this.rows = table.getRows();
    }

    /**
     * @param table
     * @param fontCell
     */
    public SpalleTable( DataTable table, Font f, String[] headers) {

        this.fontCell = f;
        this.table = table;
        this.n_col = headers.length;
        this.headers = headers;
        this.rows = table.getRows();
    }

    /**
     * @param n_col
     * @throws DocumentException
     */
    private void createTable(int n_col) throws DocumentException {
    	
    	datatable = new Table( n_col );
        //datatable.setHorizontalAlignment(Element.ALIGN_LEFT);

        if (widths_float != null) {
        	datatable.setWidths(widths_float);
        } else if (widths != null) {
        	datatable.setWidths(widths);
        }
        //table.setWidthPercentage(100); // percentage
        
        //headers
        if (show_headers) {
            if (headers == null) {
                addHeaders();
            } else {
                addHeaders(headers);
            }
        }

        insertRows();
        if (currentRow < rows) {
            insertEmptyRows(rows - currentRow);
        }

        if ( reading_type == FORWARD_ONLY ){
            updateTable(table);
        }
        
    }
    
    public void setColumnFonts( Font[] f ){
        column_fonts = f;
    }
    
    public void setColumFont( Font f, int n ){
        if ( column_fonts == null ){
            column_fonts = new Font[ n_col ];
        }
        column_fonts[ n - 1  ] = f;
    }

 
    /**
     * 
     * @param records
     */
    public void updateTable( DataTable records) {
        if (records.getRows() > 0) {
            for (int i = 1; i <= writtenRows; i++) {
                records.DeleteRow(1);
            }
        }
    }
    
    public void setMinumunRows( int rows ){
    	this.rows = rows;
    }

    /**
     * @param widhts
     */
    public void setReadingMode( int type ) {
        this.reading_type = type;
    }
    	
    /**
     * @param widhts
     */
    public void setFontHeader(Font f) {
        this.fontHeader = f;
    }

    /**
     * @param widhts
     */
    /*public void setFixedRowHeight(float h) {
        this.fixed_height = h;
    }*/

    /**
     * @param widhts
     */
    public void setWidths(int[] w) {
        this.widths = w;
    }

    /**
     * @param widhts
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @param widhts
     */
    public void setCellAllignment(int[] a) {
        this.cell_alignment = a;
    }

    
    public void setCellAllignment( int cell_alignment ) {
		this.cell_alignment_common = cell_alignment;
	}

    
    
    /**
     * @param widhts
     */
    public void setHeaderAllignment(int[] a) {
        this.header_alignment = a;
    }

    /**
     * @param widhts
     */
    public void setColumnDataType(int[] type) {
        this.column_data_type = type;
    }

    public void setColumnDataType(int type) {
        this.column_data_type_common = type;
    }
    
    /**
     * @param widhts
     */
    public void setWidths(float[] w) {
        this.widths_float = w;
    }

    /**
     * @throws DocumentException
     */
    private void insertEmptyRows(int rows) throws DocumentException {
        String cellValue = "\0\0\0\0";

        for (int i = 1; i <= rows; i++) {
            currentRow++;
            for (int z = 1; z <= n_col; z++) {
                insertCell(cellValue, z);
            }
          
        }
    }

    /**
     * @param columnName
     * @param datatable
     * @param fontCell
     * @throws BadElementException 
     */
    protected void addHeader(int column_number, String columnName,
            Table datatable, boolean userDefinedHeader) throws BadElementException {

        //se gli header sono definiti dall'utente
        if (!userDefinedHeader) {
            columnName = columnName.toLowerCase();
        }

        //opzioni sempre settate per gli headers
        datatable.setBorder(Rectangle.BOX);
        datatable.setBackgroundColor(header_color);

        Cell cell = new Cell( new Phrase(columnName, fontHeader) );
        allignHeader(column_number, cell );

        datatable.addCell( cell );
    }

    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * @param datatable
     * @param i
     * @throws DocumentException
     */
    private void insertRow(int i) throws DocumentException {

        String cellValue;
        for (int z = 1; z <= n_col; z++) {
            cellValue = (String) table.getElement(i, z);
            insertCell(cellValue, z);
        }
    }

    private void insertCell(String cellValue, int column) throws BadElementException {

        Font f_temp;
        
        if (cellValue == null) {
            cellValue = "";
        }
        
        datatable.setBackgroundColor(CELLS_COLOR);
                
        //font della cella
        if ( column_fonts != null && column_fonts[ column - 1 ] != null ){
            f_temp = column_fonts[ column - 1 ] ;
        }
        
        else {
            f_temp = fontCell;
        }
        
        Cell cell = formatCellValue(column, cellValue, f_temp );

        //allinea la cella
        allignCell(column, cell);

        //cell.setFixedHeight(fixed_height);

        datatable.addCell(cell);

    }
    
    

   
	/**
     * @param column_number
     * @param cell
     */
    public void allignCell(int column_number, Cell cell) {
        if (cell_alignment != null) {
            cell.setHorizontalAlignment(cell_alignment[column_number - 1]);
        }
        else if ( cell_alignment_common != 0 ) {
        	cell.setHorizontalAlignment( cell_alignment_common );
        }
        else return;        
    }

    /**
     * @param column_number
     * @param cell
     */
    public void allignHeader(int column_number, Cell cell) {
        if (header_alignment != null) {
            cell.setHorizontalAlignment(header_alignment[column_number - 1]);
        } else {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
    }

    /**
     * @param h
     * @param list
     * @return
     */
    private boolean contains(int h, int[] list) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] == h) { return true; }
        }
        return false;
    }

    /**
     * @param column_number
     * @param value
     * @throws BadElementException 
     */
    public Cell formatCellValue(int column_number, String value, Font f) throws BadElementException {

        if (column_data_type != null) { 
        	return new Cell(new Phrase(PdfUtil
                .format(value, column_data_type[column_number - 1]), f)); }
        else if ( column_data_type_common != 0 ) {
        	return new Cell(new Phrase(PdfUtil
                    .format(value, column_data_type_common ) , f));
        }
        
        else {
        	return new Cell(new Phrase(value, f));
        }
    }

    /**
     * @param table
     * @throws DocumentException
     */
    private void insertRows() throws DocumentException {
        for (int i = 1; /*i <= rows && */i <= table.getRows(); i++) {
            currentRow++;
            writtenRows++;
            insertRow(i);
        }
    }

    /**
     * @throws DocumentException
     */
    protected void addHeaders(String[] headers) throws DocumentException {

        String columnName;
        //crea i titoli delle colonne
        for (int i = 0; i < headers.length; i++) {
            columnName = headers[i];
            addHeader(i + 1, columnName, datatable, true);
        }
    }

    /**
     * @throws DocumentException
     */
    private void addHeaders() throws DocumentException {

        String columnName;
        //crea i titoli delle colonne
        for (int i = 1; i <= table.getCol(); i++) {
            columnName = table.getColumnNameAt(i);
            addHeader(i, columnName, datatable, false);
        }
    }
}