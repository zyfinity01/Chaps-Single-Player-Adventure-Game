package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
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
  private static int tick;

  /**
   * Game maze.
   */
  public Maze maze;

  /**
   * Game is paused.
   */
  private boolean isPaused;

  private BufferedImage background;

  /**
   * Create the panel.
   *
   * @param maze Game maze
   */
  public GamePanel(WindowActions actions, Maze maze) {
    this.maze = maze;
    
    try {
      background = ImageIO.read(new File("resources//images//game_background.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Component Container
    setBorder(new EmptyBorder(5, 5, 5, 5));
    setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    setOpaque(false);

    // Drawing Canvas
    gameCanvas = new GameCanvas();
    gameCanvas.setPreferredSize(new Dimension(500, 500));
    gameCanvas.setOpaque(false);
    add(gameCanvas);

    var sidePanel = new JPanel();
    sidePanel.setOpaque(false);
    sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

    var actionButtons = new ActionButtons(actions);
    sidePanel.add(actionButtons);

    // Game statistics
    statsPanel = new StatePanel();
    sidePanel.add(statsPanel);

    add(sidePanel);

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
   * Gets current tick.
   */
  public static int getTick() {
    return tick;
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
  }
}
