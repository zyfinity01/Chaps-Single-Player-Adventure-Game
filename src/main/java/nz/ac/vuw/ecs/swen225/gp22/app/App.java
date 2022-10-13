package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
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
   * If the game is being replayed.
   */
  private boolean replaying;

  /**
   * File chooser for loading and saving.
   */
  private final JFileChooser fileChooser;

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

    // file chooser
    fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));

    // Display window
    pack();
    setVisible(true);
  }

  /**
   * Get XML file from user.
   *
   * @return file path
   */
  private String getXmlFileFromUser() {
    int returnVal = fileChooser.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().toString();
    }

    return null;
  }

  @Override
  public void move(Direction direction) {
    if (maze == null || gamePanel == null) {
      return;
    }
    if (maze.canMoveChap(direction) && !gamePanel.isPaused() && !replaying) {
      maze.moveChap(direction);
    }
  }

  @Override
  public void pause() {
    if (gamePanel != null) {
      gamePanel.setPause(true);
    }
  }

  @Override
  public void unpause() {
    if (gamePanel != null) {
      gamePanel.setPause(false);
    }
  }

  @Override
  public void togglePause() {
    if (gamePanel != null) {
      gamePanel.setPause(!gamePanel.isPaused());
    }
  }

  @Override
  public void saveAndExit() {
    /*
      TODO: save level state so that it can be resumed later.
    */
    // recorder.saveLevel();
    exit();
  }

  @Override
  public void exit() {
    /*
      TODO: save level number so that next time game started.
      the first level is that.
    */
    System.exit(0);
  }

  @Override
  public void getGameAndResume() {
    String xmlPath = getXmlFileFromUser();
    if (xmlPath == null) {
      return; // user didn't select a file
    }

    // renderer = new Renderer(xmlPath);
    // startLevel(renderer.getLevel());
  }

  @Override
  public void startLevel(int level) {
    requestFocusInWindow();

    replaying = false; 
    maze = Persistency.loadGame("level" + level + ".xml", 17, 17);

    // todo add recorder
    Recorder recorder = new Recorder(level, false, null);
    gamePanel = new GamePanel(maze, recorder, new PlayingButtons(this), false);
    setContentPane(gamePanel);
    gamePanel.startLevel(level);

    keyController.setRecorder(recorder);

    pack(); // resize to fit new content
  }

  @Override
  public void replayLevel(int level) {
    requestFocusInWindow();

    String xmlPath = getXmlFileFromUser();
    if (xmlPath == null) {
      return; // user didn't select a file
    }

    replaying = true;

    maze = Persistency.loadGame("level" + level + ".xml", 17, 17);
    Recorder recorder = new Recorder(level, true, xmlPath);

    gamePanel = new GamePanel(maze, recorder, new ReplayingButtons(this), true);
    setContentPane(gamePanel);
    
    pause();
    gamePanel.startLevel(level);

    pack(); // resize to fit new content
  }

  @Override
  public void setReplaySpeed(double speed) {
    if(gamePanel != null){
      gamePanel.setSpeed(speed);
    }
  }

  @Override
  public void stepReplay() {
    /*
     * todo
     */
    // var nextTick = recorder.getNextTick();
    //gamePanel.setTick(nextTick);
  }

}
