package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
      startLevel(2);
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
   * @param isOpening If the file is being opened or saved.
   *
   * @return file path
   */
  private String getXmlFileFromUser(boolean isOpening) {
    int returnVal = isOpening
        ? fileChooser.showOpenDialog(this) :
        fileChooser.showSaveDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().toString();
    }

    return null;
  }

  @Override
  public void move(Direction direction) {
    if (maze.canMoveChap(direction) && !gamePanel.isPaused() && !replaying) {
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
    // todo once recorder is finished
    String pathToSave = getXmlFileFromUser(false);
    // recorder.saveLevel();
    exit();
  }

  @Override
  public void exit() {
    System.exit(0);
  }

  @Override
  public void getGameAndResume() {
    String xmlPath = getXmlFileFromUser(true);
    if (xmlPath == null) {
      return; // user didn't select a file
    }

    // todo once recorder is finished
    // renderer = new Renderer(xmlPath);
    // startLevel(renderer.getLevel());
  }

  @Override
  public void startLevel(int level) {
    requestFocusInWindow();

    replaying = false; 
    maze = Persistency.loadGame("level" + level + ".xml", 17, 17);

    // todo add recorder
    gamePanel = new GamePanel(maze, null, new PlayingButtons(this), false);
    setContentPane(gamePanel);
    gamePanel.startLevel(level);

    keyController.setRecorder(new Recorder(level));

    pack(); // resize to fit new content
  }

  @Override
  public void replayLevel(int level) {
    requestFocusInWindow();

    String xmlPath = getXmlFileFromUser(true);
    if (xmlPath == null) {
      return; // user didn't select a file
    }

    replaying = true;

    maze = Persistency.loadGame("level" + level + ".xml", 17, 17);
    //Recorder recorder = new Recorder(level);

    gamePanel = new GamePanel(maze, null, new ReplayingButtons(this), true);
    setContentPane(gamePanel);
    
    pause();
    gamePanel.startLevel(level);

    pack(); // resize to fit new content
  }

  @Override
  public void stepReplay() {
    // todo once recorder is finished
    // var nextTick = recorder.getNextTick();
    //gamePanel.setTick(nextTick);
  }

  /**
   * Get new speed from user.
   */
  public void setReplaySpeed() {
    try {
      var speed = Double.parseDouble(
          JOptionPane.showInputDialog("Enter a speed between 0 and 1"));
      if (speed < 0 || speed > 1) {
        throw new NumberFormatException();
      }
      gamePanel.setSpeed(speed);
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(null, "Invalid speed");
    }
  }

}
