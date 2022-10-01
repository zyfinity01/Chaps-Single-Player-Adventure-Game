package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * UI Container displaying game statistics.
 *
 * @author Sam Redmond, 300443508
 */
public class StateWindow extends JPanel {

  /**
   * The games current level.
   */
  private JLabel currentLevel;

  /**
   * Countdown till game over.
   */
  private JLabel timeLeft;

  /**
   * Count of chips remaining in level.
   */
  private JLabel chipsLeft;

  /**
   * Count of keys remaining in level.
   */
  private JLabel keysLeft;

  /**
   * Displays Game Information.
   */
  public StateWindow() {
    setLayout(new GridLayout(4, 2, 5, 10));
    
    add(new JLabel("Level:"));
    currentLevel = new JLabel("0");
    add(currentLevel);
    
    add(new JLabel("Time Left:"));
    timeLeft = new JLabel("0.00");
    add(timeLeft);

    add(new JLabel("Keys Left:"));
    keysLeft = new JLabel("0");
    add(keysLeft);
    
    add(new JLabel("Chips Left:"));
    chipsLeft = new JLabel("0");
    add(chipsLeft);
  }

  public void setLevel(int level) {
    currentLevel.setText(String.valueOf(level));
  }

  public void setTime(double time) {
    timeLeft.setText(String.format("%.2fs", time));
  }

  public void setChipsLeft(int chips) {
    chipsLeft.setText(String.valueOf(chips));
  }

  public void setKeysLeft(int keys) {
    keysLeft.setText(String.valueOf(keys));
  }

}
