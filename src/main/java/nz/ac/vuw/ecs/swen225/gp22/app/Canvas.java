package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Wall;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Canvas to draw on.
 *
 * @author Sam Redmond, 300443508
 */
public class Canvas extends JPanel {

  @Override
  public void paint(Graphics graphics) {
    // TODO: Pass details needed
    Tile[][] tiles = {{new Wall(), new Chap()}, {null, new Wall()}};
    Renderer.render(new Maze(tiles, 2, 2), (Graphics2D) graphics);
  }
}
