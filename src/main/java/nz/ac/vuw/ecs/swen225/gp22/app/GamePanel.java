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
import nz.ac.vuw.ecs.swen225.gp22.domain.State;
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
   * Path to background of game.
   */
  private static final String BG_PATH = "resources//images//game_background.png";

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
   * Background of game.
   */
  private BufferedImage background;

  /**
   * Timer loop for game tick.
   */
  private Timer timer;

  /**
   * Game recorder.
   */
  public Recorder recorder;

  /**
   * Game actions.
   */
  public WindowActions actions;

  /**
   * If the game is being recorded or not.
   */
  private boolean isReplaying;

  /**
   * Create the panel.
   *
   * @param maze Game maze
   * @param recorder Game recorder
   * @param actionButtons Action Buttons
   * @param isReplaying Is game replaying
   */
  public GamePanel(Maze maze, Recorder recorder, WindowActions actions,
      JPanel actionButtons, boolean isReplaying) {
    this.maze = maze;
    this.actions = actions;
    this.isReplaying = isReplaying;
    this.recorder = recorder;

    try {
      background = ImageIO.read(new File(BG_PATH));
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

    // Side panel
    var sidePanel = new JPanel();
    sidePanel.setOpaque(false);
    sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
    add(sidePanel);

    // Action buttons
    sidePanel.add(actionButtons);

    // Game statistics
    statsPanel = new StatePanel();
    sidePanel.add(statsPanel);

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
    // handle game transitions
    if (maze.getState() != State.Running) {
      timer.stop();
      showStateMessage();

      int level = Math.min(
          maze.getState() == State.Complete
          ? maze.getLevel() + 1 : maze.getLevel(), 2);
      
      actions.startLevel(level);
    }

    // recorder of game
    if (recorder != null) {
      recorder.setTick(tick);

      if (isReplaying && !isPaused) {
        Direction dir = recorder.doPlayerMovement(tick);
        if (dir != null && maze.canMoveChap(dir)) {
          maze.moveChap(dir);
        }
      }
    }

    // only update stats once a second
    if (!isPaused && tick % (1000 / TICK_RATE) == 0) {
      maze.tick();
      updateStats();
    }
    
    // redraw canvas
    gameCanvas.update(maze);

    if (!isPaused) {
      tick++;
    }
  }

  /**
   * Show messages about current state.
   */
  private void showStateMessage() {
    if (maze.getState() == State.Complete) {
      actions.showPopup("You won!");
    } else if (maze.getState() == State.Dead) {
      actions.showPopup("You died!");
    }

    if (maze.getState() == State.OutOfTime) {
      actions.showPopup("You ran out of time!");
    }
  }

  /**
   * Update stats with current level.
   *
   * @param level new level
   */
  private void updateStats() {
    statsPanel.setTime(maze.getTimeLeft());
    statsPanel.setChipsLeft(maze.getCountOfMazeTiles(Treasure.class));
    statsPanel.setKeysLeft(maze.getCountOfMazeTiles(Key.class));
    statsPanel.setInventory(maze.getInventory());
  }

  /**
   * Set level text.
   *
   * @param level updated level
   */
  public void startLevel(int level) {
    statsPanel.setLevel(level);
  }

  /**
   * Return paused state of game.
   *
   * @return if game is paused or not
   */
  public boolean isPaused() {
    return isPaused;
  }

  /**
   * Set game pause state.
   *
   * @param isPaused new value
   */
  public void setPause(boolean isPaused) {
    this.isPaused = isPaused;
    Renderer.setShowPauseText(isPaused && !isReplaying);
  }

  /**
   * Set speed of game updates.
   *
   * @param speed new speed
   */
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

  /**
   * Gets current tick.
   *
   * @return current tick
   */
  public int getTick() {
    return tick;
  }
  
  /**
   * Display the game background.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
  }
}
