package progetto.presentation.businessDelegate.stampa;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * classe DataTable
 *
 * Nuovo tipo che serve
 * per avere una matrice 2D
 *
 * Creata da Andrea Cavalieri.
 * Data Creazione : 01-04-1996
 * Data Ultima Modifica : 01-04-1996
 */
public class DataTable implements Serializable {
    
    /**
     * il vettore con i nomi di colonna  
     */    
    protected      Vector    hvectColumnName;
    
    /**
     * il vettore con i tipi di colonna  
     */    
    private      Vector    hvectColumnType;
    
    /**
     * il vettore con i dati  
     */    
    protected    Vector    hvectOr;

    /**
     * il numero di righe  
     */    
    private      int       hintRow   = -1;
    
    /**
     * Costruttore  
     */    
    public DataTable() {
        hvectOr = new Vector(10,5);
        hvectColumnName = new Vector(10,5);
        this.hvectColumnType = new Vector(10,5);
    }
    
    /**
     * Aggiunge righe in maniera sequenziale  
     */    
    public void addRow() {
        Vector lvectOr;
        
        lvectOr = new Vector(10,5);
        hvectOr.addElement(lvectOr);
        ++hintRow;
    }
    
    /**
     * Non implementato
     * @return java.util.Enumeration  
     */    
    Enumeration getRowEnumeration() {
        return null;
    }
    
    /**
     * Aggiunge una riga
     * @param aVector java.util.Vector  
     */    
    public void addRow(Vector aVector) {
        hvectOr.addElement(aVector);
        ++hintRow;
    }

    /**
     * Ritorna il n. di righe dalla DataTable
     * @return int  
     */    
    public int getRows() {
        return hintRow + 1;
    }
    
    /**
     * Aggiunge elementi nella posizione
     * riga corrente, colonna corrente.
     * @param aElement  java.lang.Object
     */    
    public void addElement(Object aElement) {
        ((Vector)hvectOr.elementAt(hintRow)).addElement(aElement);
    }

    /**
     * Aggiunge il nome della colonna
     * @param aColumn  java.lang.String
     */    
    public void addColumnName(String aColumn) {
        this.hvectColumnName.addElement(aColumn.toUpperCase());
    }
    
    /**
     * Aggiunge il type della colonna
     * @param aType  int
     */    
    public void addColumnType(int aType) {
        this.hvectColumnType.addElement((new Integer(aType)));
    }

    /**
     * Inserisce un elemento nella posizione
     * riga corrente, colonna aCol
     * @param aElement java.lang.Object
     * @param aCol  int
     */    
    public void insertElementAt(Object aElement , int aCol) {
        ((Vector)hvectOr.elementAt(hintRow)).insertElementAt(aElement , aCol);
    }

    /**
     * Ritorna l'elemento in posizion riga-colonna
     * @param row int
     * @param col int
     * @return  java.lang.Object
     */    
    public Object getElement(int row, int col) {
        Vector       lvectOr;
        Object       item = null;
        
        if (row<=hintRow+1) {
            lvectOr=(Vector)hvectOr.elementAt(row-1);
            if ( col <= lvectOr.size() )
                item = lvectOr.elementAt( col - 1 );
        }
        return item;
    }
    
    /**
     * Restutuisce il numero di elementi
     * @return int  
     */    
    public int getCol() {
        return ( (Vector)hvectOr.elementAt(hintRow) ).size();
    }
    
    /**
     * Restutuisce il numero di elementi della i-esima riga
     * @param row int
     * @return  int
     */    
    public int getNofElements(int row) {
        int result;
        result = ( (Vector)hvectOr.elementAt(row-1) ).size();
        return result;
    }

    /**
     * Restituisce il Vector con la i-esima riga
     * @param row int
     * @return java.util.Vector 
     */    
    public Vector getVector(int row) {
        return (Vector)hvectOr.elementAt(row-1);
    }
    
    /**
     * Restituisce il nome della colonna index
     * @param index int
     * @return  java.lang.String
     */    
    public String getColumnNameAt(int index) {
        return (String)hvectColumnName.elementAt(index-1);
    }
    
    /**
     * Restituisce il tipo della colonna index
     * @param index int
     * @return  int
     */    
    public int getColumnTypeAt(int index) {
        return ((Integer)hvectColumnType.elementAt(index-1)).intValue();
    }

    /**
     * Restituisce la posizione della colonna columnName
     * @param columnName java.lang.String
     * @return  int
     */    
    public int getIndexOfColumnName(String columnName) {
        String cN = columnName.toUpperCase();
        String col;
        for(int i = 0 ; i< this.hvectColumnName.size(); i++) {
            col = (String) this.hvectColumnName.elementAt(i);
            if (col.equals(cN)) {
                return i+1;
            }
        }
        
        return -1;
    }

    /**
     * Setta la riga row al vettore avectOr
     * @param avectOr java.util.Vector
     * @param row  int
     */    
    public void SetVectorAtRow(Vector avectOr , int row) {
        hvectOr.setElementAt(avectOr , row-1);
    }

    /**
     * Setta l'ultima riga al vettore avectOr
     * @param avectOr  java.util.Vector
     */    
    public void SetVectorAtLastRow(Vector avectOr) {
        hvectOr.setElementAt(avectOr , hintRow);
    }

    /**
     * Cancella la riga aintRow
     * @param aintRow  int
     */    
    public void DeleteRow(int aintRow) {
        hvectOr.removeElementAt(aintRow - 1);
        hintRow--;
    }
    
    /**
     * Setta l'elemento row-col al valore aobjeCt
     * @param row int 
     * @param col int 
     * @param aobjeCt java.lang.Object
     */    
    public void SetElement(int row, int col , Object aobjeCt) {
        Vector       thisVector;
        
        if (row<=hintRow+1) {
            thisVector=(Vector)hvectOr.elementAt(row-1);
            thisVector.setElementAt(aobjeCt , col-1);
        }
    }
}