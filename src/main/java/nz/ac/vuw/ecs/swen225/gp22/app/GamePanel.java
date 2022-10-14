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
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

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

  private BufferedImage background;

  private Timer timer;

  public Recorder recorder;

  private boolean isReplaying;

  /**
   * Create the panel.
   *
   * @param maze Game maze
   */
  public GamePanel(Maze maze, Recorder recorder, JPanel actionButtons, boolean isReplaying) {
    this.maze = maze;
    this.isReplaying = isReplaying;
    this.recorder = recorder;
    
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

    sidePanel.add(actionButtons);

    // Game statistics
    statsPanel = new StatePanel();
    sidePanel.add(statsPanel);

    add(sidePanel);

    // Game loop
    timer = new Timer(TICK_RATE, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        gameLoop(); 
      }
    });

    timer.start();
  }

  /**
   * Update each tick.
   */
  private void gameLoop() {
    // recorder of game
    if (recorder != null) {
      recorder.setTick(tick);

      if (isReplaying) {
        Direction dir = recorder.doPlayerMovement(tick);
        if (dir != null && maze.canMoveChap(dir)) {
          maze.moveChap(dir);
        }
      }
    }

    // only update stats once a second
    if (!isPaused && tick % (1000 / TICK_RATE) == 0) {
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
    Renderer.setShowPauseText(isPaused);
  }

  public void setSpeed(double speed) {
    timer.setDelay((int) (TICK_RATE * speed));
  }

  /**
   * Update game tick for skipping.
   *
   * @param tick new tick
   */
  public void setTick(int tick) {
    this.tick = tick;
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
  }
}
