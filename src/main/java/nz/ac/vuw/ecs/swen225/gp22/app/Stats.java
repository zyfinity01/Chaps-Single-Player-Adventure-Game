package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Stats extends JPanel {
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
   * Displays Game Information.
   */
  public Stats() {
    setLayout(new GridLayout(3, 2, 5, 10));
    
    add(new JLabel("Level:"));
    currentLevel = new JLabel("0");
    add(currentLevel);
    
    add(new JLabel("Time Left:"));
    timeLeft = new JLabel("0.00");
    add(timeLeft);
    
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
}
