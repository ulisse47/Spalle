package progetto.presentation.util;

/**
 * Created by IntelliJ IDEA.
 * User: Andrea
 * Date: 13-set-2003
 * Time: 17.11.52
 * To change this template use Options | File Templates.
 */
public interface CommandFactory {
    public Command createCommand(String action);
}
