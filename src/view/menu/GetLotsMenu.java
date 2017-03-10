package view.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import controller.Controller;
import model.modifylists.ModifyList;
import model.modifylists.ModifyListFactoryImpl;
import view.frames.OperationsFramesFactoryImpl;

/**
 * Base menu.
 *
 */

public class GetLotsMenu extends JMenu {

    /**
     * 
     */
    private static final long serialVersionUID = -2993920227347864515L;
/**
 * 
 * @param controller controller
 */
    public GetLotsMenu(final Controller controller) {
        super("Get Lots");
        JMenuItem menuItem = new JMenuItem("Alfabetically sorted");
        menuItem.addActionListener(e -> {
            setTextArea(controller, new ModifyListFactoryImpl().alphabeticalSorting());
        });
        this.add(menuItem);
        menuItem = new JMenuItem("Only expiring");
        menuItem.addActionListener(e -> {
            setTextArea(controller, new ModifyListFactoryImpl().onlyExpiring());
        });
        this.add(menuItem);
        menuItem = new JMenuItem("All");
        /*
         * with null  @param, @return the list of lots order by insertion 
         */
        menuItem.addActionListener(e -> {
            setTextArea(controller, null);
        });
        this.add(menuItem);
    }

    private void setTextArea(final Controller controller, final ModifyList ml) {
        new OperationsFramesFactoryImpl().getListOfLots(controller, controller.getList(ml));
    }
}
