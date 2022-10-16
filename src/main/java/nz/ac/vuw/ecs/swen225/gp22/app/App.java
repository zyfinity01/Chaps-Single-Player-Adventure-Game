package nz.ac.vuw.ecs.swen225.gp22.app;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
      toMainMenu();
    } else {
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
   * Swap current game panel.
   *
   * @param panel new panel to display
   */
  private void swapPanel(JPanel panel) {
    setContentPane(panel);
    pack(); // resize to fit new content
    requestFocusInWindow(); // allow keypress focus
  }

  /**
   * Get save level path for level.
   *
   * @param level level to find
   * @return xml path file
   */
  private String getGameSavePath(int level) {
    return "save_level_" + level + ".xml";
  }

  /**
   * Set context to main menu.
   */
  @Override
  public void toMainMenu() {
    swapPanel(new StartPanel(this));
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

  /**
   * Move player if possible.
   */
  @Override
  public void move(Direction direction) {
    if (maze == null || gamePanel == null) {
      return;
    }
    if (maze.canMoveChap(direction) && !gamePanel.isPaused() && !replaying) {
      maze.moveChap(direction);
    }
  }

  /**
   * Pause game.
   */
  @Override
  public void pause() {
    if (gamePanel != null) {
      gamePanel.setPause(true);
    }
  }

  /**
   * Unpause game.
   */
  @Override
  public void unpause() {
    if (gamePanel != null) {
      gamePanel.setPause(false);
    }
  }

  /**
   * Toggle pause.
   */
  @Override
  public void togglePause() {
    if (gamePanel != null) {
      gamePanel.setPause(!gamePanel.isPaused());
    }
  }

  /**
   * Save level data to xml file.
   */
  @Override
  public void save() {
    Persistency.saveGame(getGameSavePath(maze.getLevel()), maze);
  }

  /**
   * Close the program.
   */
  @Override
  public void exit() {
    System.exit(0);
  }

  /**
   * Request file from user and start level using it.
   */
  @Override
  public void getGameAndResume() {
    String xmlPath = getXmlFileFromUser(true);
    if (xmlPath == null) {
      return; // user didn't select a file
    }
    startLevel(xmlPath);
  }

  /**
   * Start level from save otherwise start fresh
   */
  @Override
  public void startLevel(int level) {
    String savedFile = getGameSavePath(level);
    if (new File(savedFile).isFile()){
      startLevel(savedFile);
    }
    else {
      startLevel("src/levels/level" + level + ".xml");
    }
  }

  /**
   * Load level and start game.
   *
   * @param xmlPath path to level
   */
  public void startLevel(String xmlPath) {
    replaying = false; 
    maze = Persistency.loadGame(xmlPath, 17, 17);
    int level = maze.getLevel();

    Recorder recorder = new Recorder(level, false, null);
    gamePanel = new GamePanel(maze, recorder, new PlayingButtons(this), false);
    
    gamePanel.startLevel(level);
    keyController.setRecorder(recorder);

    swapPanel(gamePanel);
  }

  /**
   * Replay level if exists.
   */
  @Override
  public void replayLevel(int level) {
    replaying = true;

    String replayFile = "moves_level_"+level+".xml";
    if (!new File(replayFile).isFile()){
      showPopup("Replay file does not exist, did you save it?");
      return;
    }

    Recorder recorder = new Recorder(level, true, replayFile);
    maze = Persistency.loadGame("src/levels/level" + level + ".xml", 17, 17);
    gamePanel = new GamePanel(maze, recorder, new ReplayingButtons(this), true);
  
    swapPanel(gamePanel);
    gamePanel.startLevel(level);
  }

  /**
   * Jump to next action in replay.
   */
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
    if (gamePanel == null) {
      showPopup("Please load the replay first");
      return;
    }
    try {
      var speed = Double.parseDouble(
          JOptionPane.showInputDialog("Enter a speed multiplier between 0.0 and 5.0"));
      if (speed < 0 || speed > 5) {
        throw new NumberFormatException();
      }
      gamePanel.setSpeed(speed);
    } catch (NumberFormatException ex) {
      showPopup("Invalid speed");
    }
  }

  /**
   * Display a popup message to user and pause game during.
   */
  @Override
  public void showPopup(String message) {
    pause();
    JOptionPane.showMessageDialog(null, message);
    unpause();
  }

  /**
   * Delete save file for level.
   */
  @Override
  public void clear(int level) {
    new File(getGameSavePath(level)).delete();
  }

}
