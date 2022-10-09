package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp22.domain.Door;
import nz.ac.vuw.ecs.swen225.gp22.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp22.domain.Info;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Lock;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Position;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tractor;
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;
import nz.ac.vuw.ecs.swen225.gp22.domain.Wall;

/**
 * Game renderer.
 *
 * @author North.
 */
public class Renderer {

  // TODO: rescale/crop to fit
  static int windowWidth = 300;
  static int tileWidth = 40;

  // Cached tiles
  // TODO: Find tileset
  static BufferedImage free = null;
  static BufferedImage wall;
  static BufferedImage lock;
  static BufferedImage info;
  static BufferedImage treasure;
  static BufferedImage exit;

  static BufferedImage chapUp;
  static BufferedImage chapDown;
  static BufferedImage chapLeft;
  static BufferedImage chapRight;

  static BufferedImage key_red;
  static BufferedImage key_blue;
  static BufferedImage key_green;
  static BufferedImage key_yellow;
  static BufferedImage door_red;
  static BufferedImage door_blue;
  static BufferedImage door_green;
  static BufferedImage door_yellow;

  static BufferedImage tractor_up;
  static BufferedImage tractor_down;
  static BufferedImage tractor_left;
  static BufferedImage tractor_right;

  // load images
  static {
    try {
      String s = Paths.get("").toAbsolutePath().toString();
      // free = ImageIO.read(new File("images//free.png"));
      wall = ImageIO.read(new File("images//wall.png"));
      lock = ImageIO.read(new File("images//lock.png"));
      info = ImageIO.read(new File("images//info.png"));
      treasure = ImageIO.read(new File("images//treasure.png"));
      exit = ImageIO.read(new File("images//exit.png"));
      
      chapUp = ImageIO.read(new File("images//chap_up.png"));
      chapDown = ImageIO.read(new File("images//chap_down.png"));
      chapLeft = ImageIO.read(new File("images//chap_left.png"));
      chapRight = ImageIO.read(new File("images//chap_right.png"));

      key_red = ImageIO.read(new File("images//key_red.png"));
      key_blue = ImageIO.read(new File("images//key_blue.png"));
      key_green = ImageIO.read(new File("images//key_green.png"));
      key_yellow = ImageIO.read(new File("images//key_yellow.png"));
      door_red = ImageIO.read(new File("images//door_red.png"));
      door_blue = ImageIO.read(new File("images//door_blue.png"));
      door_green = ImageIO.read(new File("images//door_green.png"));
      door_yellow = ImageIO.read(new File("images//door_yellow.png"));

      tractor_up = ImageIO.read(new File("images//tractor_up.png"));
      tractor_down = ImageIO.read(new File("images//tractor_down.png"));
      tractor_left = ImageIO.read(new File("images//tractor_left.png"));
      tractor_right = ImageIO.read(new File("images//tractor_right.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the image for a given tile.
   *
   * @param tile the tile.
   * @return the image.
   */
  private static BufferedImage image(Tile tile) {
    // can't switch on instanceof
    if (tile instanceof Wall) {
      return wall;
    }
    if (tile instanceof Key key) {
      switch (key.color()) {
        case Yellow:
          return key_yellow;
        case Blue:
          return key_blue;
        case Green:
          return key_green;
        case Red:
        default:
          return key_red;
      }
    }
    if (tile instanceof Door door) {
      switch (door.color()) {
        case Yellow:
          return door_yellow;
        case Blue:
          return door_blue;
        case Green:
          return door_green;
        case Red:
        default:
          return door_red;
      }
    }
    if (tile instanceof Lock) {
      return lock;
    }
    if (tile instanceof Info) {
      return info;
    }
    if (tile instanceof Treasure) {
      return treasure;
    }
    if (tile instanceof Exit) {
      return exit;
    }
    if (tile instanceof Tractor) {
      var tractor = (Tractor) tile;
      switch (tractor.direction()) {
        case Up:
          return tractor_up;
        case Down:
          return tractor_down;
        case Left:
          return tractor_left;
        case Right:
        default:
          return tractor_right;
      }
    }

    return free;        // instanceof null || fallback
  }

  /**
   * Gets the directionional image for chap.
   *
   * @param maze the current maze.
   * @return chaps image.
   */
  private static BufferedImage chap(Maze maze) {
    switch (maze.getChapDirection()) {
      case Up:
        return chapUp;
      case Down:
        return chapDown;
      case Left:
        return chapLeft;
      case Right:
      default:
        return chapRight;
    }
  }

  /**
   * Renders the maze.
   *
   * @param maze maze to render.
   * @param image graphics drawing context.
   */
  public static void render(Maze maze, Graphics2D image) {
    // Recenter the map around the player
    Position position = maze.getChapPosition();
    int offsetX = (int) (position.x() * tileWidth * -1 + (windowWidth * 0.5));
    int offsetY = (int) (position.y() * tileWidth * -1 + (windowWidth * 0.5));
    image.translate(offsetX, offsetY);

    image.drawImage(free, null, 20, 20);

    for (int x = 0; x < maze.getCols(); x++) {
      for (int y = 0; y < maze.getRows(); y++) {
        image.drawImage(image(maze.getTiles()[y][x]), null, x * tileWidth, y * tileWidth);
      }
    }
    
    image.drawImage(chap(maze), null, maze.getChapPosition().x() * tileWidth,
        maze.getChapPosition().y() * tileWidth);
  }
}

