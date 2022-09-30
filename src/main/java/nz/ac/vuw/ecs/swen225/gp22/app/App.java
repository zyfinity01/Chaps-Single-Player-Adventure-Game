package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;

/**
 * GUI Window.
 *
 * @author Sam Redmond, 300443508
 *
 */
public class App extends JFrame implements WindowActions {

  /**
   * How often the game updates in milliseconds.
   */
  private static final int TICK_RATE = 100;

  /**
   * Panel holding all UI components.
   */
  private final JPanel contentPane;

  /**
   * Panel for drawing game.
   */
  private final GameCanvas canvas;
  
  /**
   * Current game statistics.
   */
  private final StateWindow stats;

  /**
   * Game maze.
   */
  private final Maze maze;

  /**
   * Create the frame.
   */
  public App() {
    // Window Settings
    setTitle("Chaps Challenge");
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1270, 720);

    // Key Listener
    addKeyListener(new KeyController(this));
    
    // Component Container
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
    setContentPane(contentPane);

    // Drawing Canvas
    canvas = new GameCanvas();
    canvas.setPreferredSize(new Dimension(500, 500));
    contentPane.add(canvas);
    
    // Game statistics
    stats = new StateWindow();
    contentPane.add(stats);

    // Load maze
    stats.state.set("currentLevel", 1);
    maze = Persistency.loadGame("level1.xml", 16, 17);

    // Start game ticker
    new javax.swing.Timer(TICK_RATE, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        gameLoop();
      }
    }).start();

    // Display window
    pack();
    setVisible(true);
  }

  /**
   * Update each tick.
   */
  public void gameLoop() {
    canvas.update(maze);

    var timeLeft = (Double) stats.state.get("timeLeft");
    //if (timeLeft <= 0) {
    //  gameOver = true;
    //}

    // update time left
    stats.state.set("timeLeft", timeLeft - 0.001);
  }

  /**
   * Restrict movement when paused.
   *
   * @param direction direction to move
   */
  public void move(Direction direction) {
    if (!((Boolean) stats.state.get("isPaused"))) {
      maze.moveChap(direction);
    }
  }

  @Override
  public void moveUp() {
    move(Direction.Up);
  }

  @Override
  public void moveDown() {
    move(Direction.Down);
  }

  @Override
  public void moveRight() {
    move(Direction.Right);
  }

  @Override
  public void moveLeft() {
    move(Direction.Left);
  }

  @Override
  public void pause() {
    var paused = (Boolean) stats.state.get("isPaused");
    stats.state.set("isPaused", !paused);
  }

  @Override
  public void closeDialog() {

  }

  @Override
  public void exit() {
    // TODO: save current game level so that next time game started
    // the first level is that.
    System.exit(0);
  }

  @Override
  public void saveAndExit() {
  }

  @Override
  public void getGameAndResume() {

  }

  @Override
  public void startLevel1() {

  }

  @Override
  public void startLevel2() {

  }

}
