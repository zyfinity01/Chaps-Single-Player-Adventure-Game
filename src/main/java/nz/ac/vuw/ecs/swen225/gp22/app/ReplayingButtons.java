package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* Panel containing action buttons.
*
* @author Sam Redmond, 300443508
*/
public class ReplayingButtons extends ActionPanel {
  
  /**
  * Create the panel.
  *
  * @param actions action handler
  */
  public ReplayingButtons(WindowActions actions) {
    super();
    setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

    // auto play button
    var autoPlayButton = createButton("resources//images//play_button.png");
    autoPlayButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.unpause();
      }
    });
    add(autoPlayButton);

    // pause button
    var pauseButton = createButton("resources//images//pause_button.png");
    pauseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.pause();
      }
    });
    add(pauseButton);
  
    // step button
    var stepButton = createButton("resources//images//step_button.png");
    stepButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.stepReplay();
      }
    });
    add(stepButton);
    
    // set replay speed button
    var setSpeedButton = createButton("resources//images//config_button.png");
    setSpeedButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.setReplaySpeed();
      }
    });
    add(setSpeedButton);
    
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
