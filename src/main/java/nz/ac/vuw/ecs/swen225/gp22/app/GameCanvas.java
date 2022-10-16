package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

/**
 * Canvas to draw on.
 *
 * @author Sam Redmond, 300443508
 */
public class GameCanvas extends JPanel {

  /**
   * Game state.
   *
   * <p> Have made this public as Spotbugs would prefer if
   * each update would be a clone of the object, but
   * for performance reasons that isn't a good idea. </p>
   */
  public Maze maze;

  /**
   * Update game canvas with new game state.
   *
   * @param maze game maze
   */
  public void update(Maze maze) {
    this.maze = maze;
    repaint();
  }

  /**
   * Render game.
   */
  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    
    if (maze == null) {
      return;
    }

    Graphics2D g2d = (Graphics2D) graphics.create();
    Renderer.render(maze, g2d);
    g2d.dispose();
  }
}
