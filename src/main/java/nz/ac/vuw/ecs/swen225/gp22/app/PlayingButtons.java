package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* Panel containing action buttons.
*
* @author Sam Redmond, 300443508
*/
public class PlayingButtons extends ActionPanel {
  
  /**
  * Create the panel.
  *
  * @param actions action handler
  */
  public PlayingButtons(WindowActions actions) {
    super();
    
    /*
     * Play button.
     */
    var playButton = createButton("resources//images//play_button.png");
    add(playButton);
    playButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.unpause();
      }
    });
    
    /*
     * Pause button.
     */
    var pauseButton = createButton("resources//images//pause_button.png");
    add(pauseButton);

    pauseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.pause();
      }
    });
    
    /*
     * Load button.
     */
    var loadButton = createButton("resources//images//load_button.png");
    add(loadButton);

    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.getGameAndResume();
      }
    });
    
    /*
     * Save button.
     */
    var saveButton = createButton("resources//images//save_button.png");
    add(saveButton);

    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.save();
      }
    });
    
    /*
     * Info button.
     */
    var infoButton = createButton("resources//images//info_button.png");
    infoButton.setFocusable(false); // don't let popup redirect key input
    add(infoButton);

    infoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.showPopup(
            "How to play:\nUse arrow keys to move.\nCollect Bobs tools to access rooms.\n"
            + "Collect his all of his sammies.\nThen get to the exit to win!");
      }
    });
    
    /*
     * Exit button.
     */
    var exitButton = createButton("resources//images//exit_button.png");
    add(exitButton);

    exitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.toMainMenu();
      }
    });
    
  }

}
