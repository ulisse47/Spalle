package progetto.presentation.nb.fileType;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class SpalleDataLoader extends UniFileLoader {
    
    public static final String REQUIRED_MIME = "text/x";
    
    private static final long serialVersionUID = 1L;
    
    public SpalleDataLoader() {
        super("progetto.presentation.nb.fileType.SpalleDataObject");
    }
    
    protected String defaultDisplayName() {
        return NbBundle.getMessage(SpalleDataLoader.class, "LBL_Spalle_loader_name");
    }
    
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }
    
    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new SpalleDataObject(primaryFile, this);
    }
    
    protected String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
    
}
