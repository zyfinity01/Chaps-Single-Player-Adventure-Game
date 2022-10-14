package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

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
    
    // play button
    var playButton = createButton("resources//images//play_button.png");
    playButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.unpause();
      }
    });
    add(playButton);
    
    // pause button
    var pauseButton = createButton("resources//images//pause_button.png");
    pauseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.pause();
      }
    });
    add(pauseButton);

    // load button
    var loadButton = createButton("resources//images//load_button.png");
    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.getGameAndResume();
      }
    });
    add(loadButton);
    
    // save button
    var saveButton = createButton("resources//images//save_button.png");
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.saveAndExit();
      }
    });
    add(saveButton);
    
    // info button
    var infoButton = createButton("resources//images//info_button.png");
    infoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
            "How to play:\nUse arrow keys to move.\nCollect Bobs tools to access rooms.\n"
            + "Collect his all of his sammies.\nThen get to the exit to win!");
      }
    });
    add(infoButton);
    
    // exit button
    var exitButton = createButton("resources//images//exit_button.png");
    exitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.exit();
      }
    });
    add(exitButton);
  }

}
