/*
 * Created on 5-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package progetto.presentation.view.components;

import java.text.NumberFormat;

import javax.swing.table.AbstractTableModel;

/**
 * @author Andrea
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractBaseTableModel extends AbstractTableModel {

	protected Object[][] rowData;

	protected String[] header;

	protected NumberFormat nf;

	public AbstractBaseTableModel() {
	}

	public abstract Object[][] loadRowData();

	public abstract String[] loadHeaders();

	public AbstractBaseTableModel(Object[][] rowData, String[] header) {
		this.rowData = rowData;
		this.header = header;
	}

	public Object[][] getRowData() {
		return rowData;
	}

	/**
	 * Tutte editabili
	 */
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	/**
	 *  
	 */
	public int getRowCount() {
		return rowData.length;
	}

	/**
	 *  
	 */
	public int getColumnCount() {
		return header.length;
	}

	/**
	 * 
	 * @param aValue
	 * @param row
	 * @param column
	 */
	public void setValueAt(Object aValue, int row, int column) {
		rowData[row][column] = aValue;
	}

	/**
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		/*
		 * Object obj = rowData[rowIndex][columnIndex]; if ( obj == null ){
		 * return "null";}
		 * 
		 * double val; if ( obj instanceof Double ){ val = ( ( Double )obj
		 * ).doubleValue(); if ( Double.isNaN( val ) ){ return "not valid"; }
		 * return new Double ( val ); }
		 */
		return rowData[rowIndex][columnIndex];
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/**
	 *  
	 */
	public String getColumnName(int column) {
		return " " + header[column];
	}

}