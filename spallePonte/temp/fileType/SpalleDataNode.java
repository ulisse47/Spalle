package progetto.presentation.nb.fileType;

import java.awt.event.ActionEvent;
import org.openide.cookies.SaveCookie;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.actions.CallableSystemAction;
import progetto.presentation.nb.action.SaveAction;

public class SpalleDataNode extends DataNode {
    
    private static final String IMAGE_ICON_BASE = "SET/PATH/TO/ICON/HERE";
    
    public SpalleDataNode(SpalleDataObject obj) {
        super(obj, Children.LEAF);
            
        
      
            //setModified( true );

//        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }
    
   
    
    
//    /** Creates a property sheet. */
//    protected Sheet createSheet() {
//        Sheet s = super.createSheet();
//        Sheet.Set ss = s.get(Sheet.PROPERTIES);
//        if (ss == null) {
//            ss = Sheet.createPropertiesSet();
//            s.put(ss);
//        }
//        // TODO add some relevant properties: ss.put(...)
//        return s;
//    }
    
}
