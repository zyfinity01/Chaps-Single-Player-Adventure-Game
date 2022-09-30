package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collection;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

/**
 * Canvas to draw on.
 *
 * @author Sam Redmond, 300443508
 */
public class Canvas extends JPanel {

  @Override
  public void paint(Graphics graphics) {
    /* Temporary to avoid spotbugs while Maze link is being made */
    var tiles = new Tile[10][10];
    tiles[0][0] = new Chap();
    var maze = new Maze(tiles, 1, 1);

    Renderer.render(maze, (Graphics2D) graphics);
  }
}
