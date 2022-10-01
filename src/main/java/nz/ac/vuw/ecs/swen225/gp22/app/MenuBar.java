package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
* Menu bar for the game.
*
* @author Sam Redmond, 300443508
*/
public class MenuBar extends JMenuBar {
  
  /**
  * Create the menu bar.
  */
  public MenuBar(WindowActions actions) {
    super();
    
    var optionsMenu = new JMenu("Options");
    
    // Play/Pause Option
    var pauseItem = new JMenuItem(new AbstractAction("Toggle Pause (spacebar)") {
      public void actionPerformed(ActionEvent e) {
        actions.togglePause();
      }
    });
    optionsMenu.add(pauseItem);
    
    // Save and Exit Option
    var saveExitItem = new JMenuItem(new AbstractAction("Save and Exit (CTRL-S)") {
      public void actionPerformed(ActionEvent e) {
        actions.saveAndExit();
      }
    });
    optionsMenu.add(saveExitItem);
    
    // Exit Option
    var exitItem = new JMenuItem(new AbstractAction("Exit (CTRL-X)") {
      public void actionPerformed(ActionEvent e) {
        actions.exit();
      }
    });
    optionsMenu.add(exitItem);
    
    add(optionsMenu);
  }
  
}
