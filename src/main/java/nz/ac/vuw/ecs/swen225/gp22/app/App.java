package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.JFrame;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

/**
 * GUI Window.
 *
 * @author Sam Redmond, 300443508
 *
 */
public class App extends JFrame implements WindowActions {
  
  /**
   * Current game statistics.
   */
  private GamePanel gamePanel;

  /**
   * Game key press handler.
   */
  private final KeyController keyController;

  /**
   * Game maze.
   */
  private Maze maze;

  /**
   * Create the frame.
   */
  public App(boolean useStartScreen) {
    // Window Settings
    setTitle("Chaps Challenge");
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Menu bar
    setJMenuBar(new MenuBar(this));

    // Key Listener
    keyController = new KeyController(this);
    addKeyListener(keyController);

    if (useStartScreen) {
      // Start Screen
      setContentPane(new StartPanel(this));
    } else {
      // Game Screen
      startLevel(1);
    }

    // Display window
    pack();
    setVisible(true);
  }

  @Override
  public void move(Direction direction) {
    if (maze.canMoveChap(direction) && !gamePanel.isPaused()) {
      maze.moveChap(direction);
    }
  }

  @Override
  public void pause() {
    gamePanel.setPause(true);
  }

  @Override
  public void unpause() {
    gamePanel.setPause(false);
  }

  @Override
  public void togglePause() {
    gamePanel.setPause(!gamePanel.isPaused());
  }

  @Override
  public void saveAndExit() {
    /*
      TODO: save level state so that it can be resumed later.
    */
    exit();
  }

  @Override
  public void exit() {
    /*
      TODO: save level number so that next time game started
      the first level is that.
    */
    System.exit(0);
  }

  @Override
  public void getGameAndResume() {
    /*
     TODO: resume a saved game - this will pop up a file selector to select a saved game
     to be loaded
    */
  }

  @Override
  public void startLevel(int level) {
    maze = Persistency.loadGame("level" + level + ".xml", 17, 17);
    keyController.setRecorder(new Recorder(level));

    gamePanel = new GamePanel(maze);
    setContentPane(gamePanel);
    gamePanel.startLevel(level);

    pack(); // resize to fit new content
  }

}
