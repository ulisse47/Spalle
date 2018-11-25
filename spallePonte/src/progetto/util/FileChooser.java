package progetto.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileChooser
        extends JFileChooser {



    public static int SAVE_AS = 0;
    public static int SAVE = 1;
    public static int OPEN = 2;

    public String extension = "rtf";

    private Filter filter = new Filter ();
    private String fileName;
    private String pathName;
    private String absoluteFileName;
    private int tipo;

    private FileInputStream fin;
    private BufferedInputStream bin;
    private ObjectInputStream oin;

    private FileOutputStream fout;
    private BufferedOutputStream bout;
    private ObjectOutputStream out;

    public FileChooser ( int dialogType, String _extension, String descrizione ) {
        try {
            jbInit ( dialogType, _extension , descrizione );
        }
        catch ( Exception e ) {
            e.printStackTrace ();
        }
    }

    public void setTipo ( int tipo ) {
        this.tipo = tipo;
    }

    private void jbInit ( int dialogType, String _extension, String descrizione ) throws Exception {
        extension = _extension;
        this.setDialogTitle ( "" );
        filter.addExtension ( extension );
        filter.setDescription ( descrizione );
        filter.setExtensionListInDescription ( true );
        this.setFileFilter ( filter );
        this.addChoosableFileFilter ( filter );
        this.setAcceptAllFileFilterUsed ( false );
        this.setDialogType ( dialogType );
    }


    public void approveSelection () {
        try {
            if ( tipo == SAVE_AS ) {
                if ( saveAs () ) {
                    super.approveSelection ();
                }
            }
            if ( tipo == OPEN ) {
                if ( open () ) {
                    super.approveSelection ();
                }
            }
        }
        catch ( IOException ioe ) {
            ioe.printStackTrace ();
        }
    }

    private boolean saveAs () throws IOException {
        fileName = this.getSelectedFile ().getName ();
        pathName = this.getCurrentDirectory ().getPath ();
        int i;
        String ext;
        char[] ext_tmp;
        //se scrivo il nome senza estensione
        if ( ( i = fileName.lastIndexOf ( "." ) ) == -1 ) {
            fileName = fileName.concat ( "." + extension );
        }
        //se scrivo il nome con l'estensione
        else {
            ext = fileName.substring ( i + 1, fileName.length () );
            ext = ext.toLowerCase ();
            //se l'estensione != prg, se non � giusta quindi
            if ( !ext.equalsIgnoreCase ( extension ) ) {
                JOptionPane.showMessageDialog ( null,
                                                "L'estensione " + ext +
                                                " non è corretta!",
                                                "", JOptionPane.INFORMATION_MESSAGE );
                return false;

            }
            /*else {
              //se l'estensione � giusta ma Maiuscola la converti minuscola
              if (ext.equals( extension.toUpperCase() )) {
                ext_tmp = fileName.toCharArray();
                ext_tmp[ext_tmp.length - 3] = 'r';
                ext_tmp[ext_tmp.length - 2] = 't';
                ext_tmp[ext_tmp.length - 1] = 'f';
                fileName = String.valueOf(ext_tmp);
              }
                   }*/
        }

        File file = new File ( pathName + File.separatorChar + fileName );
        if ( file.exists () ) {
            int res = JOptionPane.showConfirmDialog ( null,
                                                      "Il file esiste già, vuoi sovrascriverlo?",
                                                      "", JOptionPane.YES_NO_OPTION );
            if ( res != JOptionPane.YES_OPTION ) {
                return false;
            }
        }
        absoluteFileName = file.getAbsolutePath ();
        return true;
    }

    public String getAbsoluteFileName () {
        return absoluteFileName;
    }

    /**
     *
     * @return
     */
    private boolean open () {
        fileName = this.getSelectedFile ().getName ();
        pathName = this.getCurrentDirectory ().getPath ();
        File file = new File ( pathName + File.separatorChar + fileName );
        if ( !file.exists () ) {
            return false;
        }
        absoluteFileName = file.getAbsolutePath ();
        return true;
    }
}