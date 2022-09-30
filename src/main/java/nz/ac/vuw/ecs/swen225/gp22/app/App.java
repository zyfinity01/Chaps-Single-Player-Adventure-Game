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
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;
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
   * Game is paused.
   */
  private boolean isPaused;

  /**
   * Number of current tick.
   */
  private int tick;

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
    isPaused = false;
    stats = new StateWindow();
    contentPane.add(stats);

    // Load maze
    maze = Persistency.loadGame("level1.xml", 16, 17);
    stats.setLevel(1);

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
    // only update time once a second
    if (tick % (1000 / TICK_RATE) == 0) {
      maze.tick();
      stats.setTime(maze.getTimeLeft());
    }
    stats.setChipsLeft(maze.getCountOfMazeTiles(Treasure.class));
    canvas.update(maze);
    tick++;
  }

  /**
   * Restrict movement when paused.
   *
   * @param direction direction to move
   */
  public void move(Direction direction) {
    if (!isPaused && maze.canMoveChap(direction)) {
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
    isPaused = !isPaused;
  }

  @Override
  public void closeDialog() {

  }

  @Override
  public void exit() {
    /*
      TODO: save current game level so that next time game started
      the first level is that.
    */
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
