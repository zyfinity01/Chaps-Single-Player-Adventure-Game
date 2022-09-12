package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * GUI Window.
 *
 * @author Sam Redmond, 300443508
 *
 */
public class App extends JFrame implements KeyListener {
  /**
   * Panel holding all UI components.
   */
  private JPanel contentPane;

  /**
   * Panel for drawing game.
   */
  private Canvas canvas;

  /**
   * Create the frame.
   */
  public App() {
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1270, 720);
    
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    setContentPane(contentPane);

    canvas = new Canvas();
    contentPane.add(canvas);

    setVisible(true);
  }

  @Override
  public void keyTyped(KeyEvent event) {}

  @Override
  public void keyPressed(KeyEvent event) {}

  @Override
  public void keyReleased(KeyEvent event) {}
}
