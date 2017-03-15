package view.menu;


import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import controller.Controller;
import view.ResourceBound;
import view.frames.AddLotsFrame;
import view.frames.OperationsFramesFactoryImpl;

/**
 *
 */
public class OperationsMenu extends JMenu {

    /**
     * 
     */
    private static final long serialVersionUID = 4790546772035194942L;
    private final JFileChooser fileChooser = new JFileChooser();
    private final ResourceBound res = new ResourceBound();
    /**
     * @param controller controller
     */

    public OperationsMenu(final Controller controller) {
        super("Operations");
        JMenuItem menuItem = new JMenuItem(this.res.setName("ADD_LOT"));
        menuItem.addActionListener(e -> {
               new AddLotsFrame(controller);
        });
        this.add(menuItem);

        final JMenuItem load = new JMenuItem(this.res.setName("LOAD"));
        menuItem = load;
        menuItem.addActionListener(e -> {
            final int retVal = this.fileChooser.showOpenDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.initialize((this.fileChooser.getSelectedFile().getPath()));
                    new OperationsFramesFactoryImpl().getListOfLots(controller, controller.getList(null));
                } catch (Exception e1) {
                    controller.getSubject().showMessageErrorView(this.res.setName("ILLEGAL_FORMAT_FILE"));
                }
                if (controller.getList(null).size() != 0) {
                    load.setEnabled(false);
                }
            }
        });
        this.add(menuItem);
        menuItem = new JMenuItem(this.res.setName("SAVE"));
        menuItem.addActionListener(e -> {
            final int retVal = this.fileChooser.showSaveDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.saveFile(this.fileChooser.getSelectedFile().getPath());
                } catch (IOException e1) {
                    controller.getSubject().showMessageErrorView(this.res.setName("SOMETHING_GOES_WRONG"));
                }
            }
        });
        this.add(menuItem);

        menuItem = new JMenuItem(this.res.setName("RESET_SUGGESTIONS"));
        menuItem.addActionListener(e -> {
            controller.resetSuggestions();
        });
        this.add(menuItem);
    }
}