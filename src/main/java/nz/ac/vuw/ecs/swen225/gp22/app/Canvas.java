package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

/**
 * Canvas to draw on.
 *
 * @author Sam Redmond, 300443508
 */
public class Canvas extends JPanel {

  @Override
  public void paint(Graphics graphics) {
    Renderer.render(null, (Graphics2D) graphics);
  }
}
