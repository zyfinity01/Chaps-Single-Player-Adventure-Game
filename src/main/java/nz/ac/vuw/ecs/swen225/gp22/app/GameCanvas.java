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

  public Maze maze;
  //private Maze maze;

  public void update(Maze maze) {
    this.maze = maze; //maze.clone();
    repaint();
  }

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
