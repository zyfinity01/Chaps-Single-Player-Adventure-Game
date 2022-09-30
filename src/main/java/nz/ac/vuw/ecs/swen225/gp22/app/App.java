package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
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
  private Maze maze;

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

    // Menu bar
    setJMenuBar(createMenu());

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

  private JMenuBar createMenu() {
    final JMenuBar menuBar = new JMenuBar();
    final JMenu optionsMenu = new JMenu("Options");

    // Play/Pause Option
    final JMenuItem pauseItem = new JMenuItem(new AbstractAction("Toggle Pause (spacebar)") {
      public void actionPerformed(ActionEvent e) {
        isPaused = !isPaused;
      }
    });
    optionsMenu.add(pauseItem);

    // Save and Exit Option
    final JMenuItem saveExitItem = new JMenuItem(new AbstractAction("Save and Exit (CTRL-S)") {
      public void actionPerformed(ActionEvent e) {
        saveAndExit();
      }
    });
    optionsMenu.add(saveExitItem);

    // Exit Option
    final JMenuItem exitItem = new JMenuItem(new AbstractAction("Exit (CTRL-X)") {
      public void actionPerformed(ActionEvent e) {
        exit();
      }
    });
    optionsMenu.add(exitItem);

    menuBar.add(optionsMenu);
    return menuBar;
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

    // only update time once a second
    if (tick % (1000 / TICK_RATE) == 0) {
      maze.tick();
      stats.setTime(maze.getTimeLeft());
    }

    // display items left
    stats.setChipsLeft(maze.getCountOfMazeTiles(Treasure.class));
    stats.setKeysLeft(maze.getCountOfMazeTiles(Key.class));
    
    // redraw canvas
    canvas.update(maze);
    tick++;
  }

  @Override
  public void move(Direction direction) {
    if (!isPaused && maze.canMoveChap(direction)) {
      maze.moveChap(direction);
    }
  }

  @Override
  public void pause() {
    isPaused = true;
  }

  @Override
  public void unpause() {
    isPaused = false;
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
  public void startLevel(String levelName) {
    maze = Persistency.loadGame(levelName, 16, 17);
  }

}
