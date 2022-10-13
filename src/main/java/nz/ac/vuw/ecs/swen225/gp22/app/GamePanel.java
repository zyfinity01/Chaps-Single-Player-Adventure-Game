package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;

/**
 * Game logic panel.
 *
 * @author Sam Redmond, 300443508
 */
public class GamePanel extends JPanel {

  /**
   * How often the game updates in milliseconds.
   */
  private static final int TICK_RATE = 100;

  /**
   * Panel displaying game stats and inventory.
   */
  private StatePanel statsPanel;

  /**
   * Panel displaying the game screen.
   */
  private GameCanvas gameCanvas;

  /**
   * Number of current tick.
   */
  private int tick;

  /**
   * Game maze.
   */
  public Maze maze;

  /**
   * Game is paused.
   */
  private boolean isPaused;

  /**
   * Create the panel.
   *
   * @param maze Game maze
   */
  public GamePanel(Maze maze) {
    this.maze = maze;

    // Component Container
    setBorder(new EmptyBorder(5, 5, 5, 5));
    setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

    // Drawing Canvas
    gameCanvas = new GameCanvas();
    gameCanvas.setPreferredSize(new Dimension(500, 500));
    add(gameCanvas);

    // Game statistics
    statsPanel = new StatePanel();
    add(statsPanel);

    // Game loop
    new javax.swing.Timer(TICK_RATE, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        gameLoop();
      }
    }).start();
  }

  /**
   * Update each tick.
   */
  private void gameLoop() {
    if (isPaused) {
      /*
       TODO: Show paused dialog
       */
      return;
    }

    // only update stats once a second
    if (tick % (1000 / TICK_RATE) == 0) {
      maze.tick();
      statsPanel.setTime(maze.getTimeLeft());
      statsPanel.setChipsLeft(maze.getCountOfMazeTiles(Treasure.class));
      statsPanel.setKeysLeft(maze.getCountOfMazeTiles(Key.class));
      statsPanel.setInventory(maze.getInventory());
    }
    
    // redraw canvas
    gameCanvas.update(maze);
    tick++;
  }

  public void startLevel(int level) {
    statsPanel.setLevel(level);
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void setPause(boolean isPaused) {
    this.isPaused = isPaused;
  }

  /**
   * Gets current tick
   */
  public int getTick(){
    return tick;
  }

}
