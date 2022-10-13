package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
* Panel containing action buttons.
*
* @author Sam Redmond, 300443508
*/
public class ActionButtons extends JPanel {
  
  /**
  * Create the panel.
  *
  * @param actions action handler
  */
  public ActionButtons(WindowActions actions) {
    setOpaque(false);
    setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
    
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
        System.out.println("this ran");
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
    
    // info button
    var infoButton = createButton("resources//images//info_button.png");
    infoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        /*
         * Todo
         */
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
  
  private JButton createButton(String imagePath) {
    JButton button = null;
    try {
      var buttonIcon = ImageIO.read(new File(imagePath));
      button = new JButton(new ImageIcon(buttonIcon));
      button.setMargin(new Insets(0, 0, 0, 0));
      button.setOpaque(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return button;
  }
}
