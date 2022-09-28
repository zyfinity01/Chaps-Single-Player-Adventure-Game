package nz.ac.vuw.ecs.swen225.gp22.renderer;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;

// I've made this static because there's really no need to store state
// TODO: There might be
// TODO: Better comments

/** Handles rendering. */
public class Renderer {

  // TODO: rescale/crop to fit
  static int windowWidth = 20;
  static int tileWidth = 40;

  // Cached tiles
  // TODO: Find tileset
  static BufferedImage free = null;
  static BufferedImage wall;
  static BufferedImage key;
  static BufferedImage door;
  static BufferedImage lock;
  static BufferedImage info;
  static BufferedImage treasure;
  static BufferedImage exit;
  static BufferedImage chap;

  // load images
  static {
    try {
      String s = Paths.get("").toAbsolutePath().toString();
      // free = ImageIO.read(new File("images//free.png"));
      wall = ImageIO.read(new File("images//wall.png"));
      key = ImageIO.read(new File("images//wall.png"));
      door = ImageIO.read(new File("images//wall.png"));
      lock = ImageIO.read(new File("images//wall.png"));
      info = ImageIO.read(new File("images//wall.png"));
      treasure = ImageIO.read(new File("images//wall.png"));
      exit = ImageIO.read(new File("images//wall.png"));
      chap = ImageIO.read(new File("images//chap.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** get the image for a given tile. */
  public static BufferedImage image(Tile tile) {
    // can't switch on instanceof
    if (tile instanceof Wall) {
      return wall;
    }
    if (tile instanceof Key) {
      return key;
    }
    if (tile instanceof Door) {
      return door;
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
    if (tile instanceof Chap) {
      // TODO: switch on direction
      return chap;
    }
    return free;        // instanceof null || fallback
  }

  /** Render. */
  public static void render(Maze maze, Graphics2D image) {
    // Affine transformation is to rescale the image - https://www.geogebra.org/m/Fq8zyEgS
    // new AffineTransformOp(new AffineTransform(0.1,0,0,0.1,0,0), AffineTransformOp.TYPE_BILINEAR)
    image.drawImage(free, null, 20, 20);
    for (int x = 0; x < maze.getCols(); x++) {
      for (int y = 0; y < maze.getRows(); y++) {
        image.drawImage(image(maze.getTiles()[y][x]), null, x * tileWidth, y * tileWidth);
      }
    }
    image.drawImage(chap, null,
        maze.getChapPosition().x() * tileWidth, maze.getChapPosition().y() * tileWidth);
  }
}

