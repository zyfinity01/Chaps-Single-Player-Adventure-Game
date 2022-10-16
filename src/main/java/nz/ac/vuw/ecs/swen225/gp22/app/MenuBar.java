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
  *
  * @param actions window actions
  */
  public MenuBar(WindowActions actions) {
    super();
    
    /*
     * Options
     */
    var optionsMenu = new JMenu("Options");
    add(optionsMenu);
    
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
        actions.save();
        actions.exit();
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

    /*
     * Loading Levels
     */
    var levelMenu = new JMenu("Load Level");
    add(levelMenu);

    // Level 1
    var levelOneItem = new JMenuItem(new AbstractAction("Level 1") {
      public void actionPerformed(ActionEvent e) {
        actions.startLevel(1);
      }
    });
    levelMenu.add(levelOneItem);

    // Level 2
    var levelTwoItem = new JMenuItem(new AbstractAction("Level 2") {
      public void actionPerformed(ActionEvent e) {
        actions.startLevel(2);
      }
    });
    levelMenu.add(levelTwoItem);

    // Custom
    var customLevelItem = new JMenuItem(new AbstractAction("Custom") {
      public void actionPerformed(ActionEvent e) {
        actions.getGameAndResume();
      }
    });

    levelMenu.add(customLevelItem);

    /*
     * Clearing Levels
     */
    var clearMenu = new JMenu("Clear Level");
    add(clearMenu);

    // Level 1
    var clearOneItem = new JMenuItem(new AbstractAction("Level 1") {
      public void actionPerformed(ActionEvent e) {
        actions.clear(1);
      }
    });

    clearMenu.add(clearOneItem);

    // Level 2
    var clearTwoItem = new JMenuItem(new AbstractAction("Level 2") {
      public void actionPerformed(ActionEvent e) {
        actions.clear(2);
      }
    });

    clearMenu.add(clearTwoItem);

    /*
     * Replay
     */
    var replayMenu = new JMenu("Replay");
    add(replayMenu);

    // Level 1
    var replayOneItem = new JMenuItem(new AbstractAction("Replay level 1") {
      public void actionPerformed(ActionEvent e) {
        actions.replayLevel(1);
      }
    });

    replayMenu.add(replayOneItem);

    // Level 2
    var replayTwoItem = new JMenuItem(new AbstractAction("Replay level 2") {
      public void actionPerformed(ActionEvent e) {
        actions.replayLevel(2);
      }
    });

    replayMenu.add(replayTwoItem);

    // Step
    var stepItem = new JMenuItem(new AbstractAction("Step (to next move)") {
      public void actionPerformed(ActionEvent e) {
        actions.stepReplay();
      }
    });

    replayMenu.add(stepItem);

    // Set replay speed
    var updateSpeedItem = new JMenuItem(new AbstractAction("Change replay speed") {
      public void actionPerformed(ActionEvent e) {
        actions.setReplaySpeed();
      }
    });

    replayMenu.add(updateSpeedItem);
    
  }
  
}
