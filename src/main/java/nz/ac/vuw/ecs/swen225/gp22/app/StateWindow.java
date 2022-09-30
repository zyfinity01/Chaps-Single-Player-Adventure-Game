package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * UI Container displaying game statistics.
 */
public class StateWindow extends JPanel implements StateUpdateHandler {

  /**
   * State of the game at a point in time.
   */
  public GameState state;

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
  public StateWindow() {
    state = new GameState(this);
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

  @Override
  public void handle(String field, Object updatedValue) {
    switch (field) {
      case "timeLeft" -> setTime((Double) updatedValue);
      case "currentLevel" -> setLevel((Integer) updatedValue);
      case "chipsLeft" -> setChipsLeft((Integer) updatedValue);
      default -> { }
    }
  }

  private void setLevel(int level) {
    currentLevel.setText(String.valueOf(level));
  }

  private void setTime(double time) {
    timeLeft.setText(String.format("%.2fs", time));
  }

  private void setChipsLeft(int chips) {
    chipsLeft.setText(String.valueOf(chips));
  }

}
