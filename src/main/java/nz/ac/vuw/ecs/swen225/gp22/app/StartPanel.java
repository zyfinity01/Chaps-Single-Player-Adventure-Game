package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Start screen panel.
 *
 * @author Sam Redmond, 300443508
 */
public class StartPanel extends JPanel {

  private BufferedImage background;

  /**
   * Create the panel.
   */
  public StartPanel(WindowActions actions) {
    setPreferredSize(new Dimension(600, 500));
    setLayout(new BorderLayout());

    try {
      background = ImageIO.read(new File("resources//images//start_screen.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    var buttonPanel = new JPanel();
    add(buttonPanel, BorderLayout.SOUTH);
    
    var startOneButton = new JButton("Level 1");
    startOneButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.startLevel(1);
      }
    });

    buttonPanel.add(startOneButton);

    var startTwoButton = new JButton("Level 2");
    startTwoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.startLevel(2);
      }
    });

    buttonPanel.add(startTwoButton);

    var replayOneButton = new JButton("Replay Level 1");
    replayOneButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.replayLevel(1);
      }
    });

    buttonPanel.add(replayOneButton);

    var replayTwoButton = new JButton("Replay Level 2");
    replayTwoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.replayLevel(2);
      }
    });

    buttonPanel.add(replayTwoButton);

    var exitButton = new JButton("Exit");
    exitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.exit();
      }
    });
    
    buttonPanel.add(exitButton);
    
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
  }

}
