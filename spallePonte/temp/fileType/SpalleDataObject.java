package progetto.presentation.nb.fileType;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import java.awt.event.ActionEvent;
import java.io.IOException;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.text.DataEditorSupport;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.nb.action.SaveAction;

public class SpalleDataObject extends MultiDataObject {

   // private boolean modified;
    
    public SpalleDataObject(FileObject pf, SpalleDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        CookieSet cookies = getCookieSet();
        //cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
        //cookies.add()
        markModified();
    }
    
    protected Node createNodeDelegate() {
        return new SpalleDataNode(this);
    }
    
    
     public synchronized void markModified() {
       // if (!modified) {
            // Newly modified, make it possible to save.
            // Note this will automatically fire a cookie change.
            getCookieSet().add(new SaveCookie() {
                public void save() {
                    doSave();
                }
            });
            setModified( true );
           // modified = true;
        //}
    }
    private synchronized void doSave() {
        //if (modified) {
            // actually save...then:
            CallableSystemAction.get( SaveAction.class ).actionPerformed( new ActionEvent( "" , 0, "") );
            //SaveCookie s = (SaveCookie)getCookie(SaveCookie.class);
            //getCookieSet().remove(s);
            setModified( false );
           // modified = false;
        //}
    }
    
    
}
