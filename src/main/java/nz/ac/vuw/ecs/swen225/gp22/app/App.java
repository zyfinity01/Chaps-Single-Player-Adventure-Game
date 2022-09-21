package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * GUI Window.
 *
 * @author Sam Redmond, 300443508
 *
 */
public class App extends JFrame implements Actions {
  /**
   * Panel holding all UI components.
   */
  private final JPanel contentPane;

  /**
   * Panel for drawing game.
   */
  private final Canvas canvas;
  
  /**
   * Current game statistics.
   */
  private final Stats stats;

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
    addKeyListener(new Controls(this));
    
    // Component Container
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
    setContentPane(contentPane);

    // Drawing Canvas
    canvas = new Canvas();
    canvas.setPreferredSize(new Dimension(500, 500));
    contentPane.add(canvas);
    
    // Game statistics
    stats = new Stats();
    contentPane.add(stats);

    pack();
    setVisible(true);
  }

  @Override
  public void moveUp() {

  }

  @Override
  public void moveDown() {

  }

  @Override
  public void moveRight() {

  }

  @Override
  public void moveLeft() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void closeDialog() {

  }

  @Override
  public void exit() {

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
