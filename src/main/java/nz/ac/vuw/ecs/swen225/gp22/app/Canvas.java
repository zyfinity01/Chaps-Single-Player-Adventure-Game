package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.*;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

/**
 * Canvas to draw on.
 *
 * @author Sam Redmond, 300443508
 */
public class Canvas extends JPanel {

  @Override
  public void paint(Graphics graphics) {
    // TODO: Pass in correct maze
    Renderer.render(null, (Graphics2D) graphics);
  }
}
