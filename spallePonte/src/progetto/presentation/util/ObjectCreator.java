package progetto.presentation.util;


/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 13-set-2003
 * Time: 17.34.03
 * To change this template use Options | File Templates.
 */
public class ObjectCreator {
    public static Object createObject(String name) {
        Object obj = null;
        try {
            obj = Class.forName(name).newInstance();
            System.out.println("ho instatziato il Command "+ name);
        } catch (Exception e) {
            System.out.println("non sono riuscito ad instanziare il Command "+ name);
            e.printStackTrace();
        }
        return obj;
    }
}
