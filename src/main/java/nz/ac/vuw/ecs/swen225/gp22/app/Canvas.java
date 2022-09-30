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
//    var tiles = new Tile[10][10];
    Tile[][] tiles = new Tile[][]{  {new Wall(), new Wall(),new Wall(), new Wall(), new Wall(), new Wall(),           new Wall(),            new Wall(),     new Wall(),         new Wall(),}
                                  , {new Wall(), null,      null,       null,       new Wall(), new Chap(),           new Info("test"), new Treasure(), new Key(Color.Blue), new Wall()}
                                  , {new Wall(), null,      new Exit(), null,       new Wall(), null,                 null,                  null,           null,               new Wall()}
                                  , {new Wall(), null,      null      , null,       new Wall(), new Door(Color.Blue), new Wall(),            new Lock(),     new Wall(),         new Wall()}
//                                  , {new Wall(),new Wall()}
//                                  , {new Wall(),new Wall()}
//                                  , {new Wall(),new Wall()}
//                                  , {new Wall(),new Wall()}
//                                  , {new Wall(),new Wall()}
//                                  , {new Wall(),new Wall()}
    };
    var maze = new Maze(tiles, 4, 10);

    Renderer.render(maze, (Graphics2D) graphics);
  }
}
