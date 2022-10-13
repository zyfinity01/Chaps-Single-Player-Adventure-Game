package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Door;
import nz.ac.vuw.ecs.swen225.gp22.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp22.domain.Info;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Lock;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Position;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;
import nz.ac.vuw.ecs.swen225.gp22.domain.Wall;

/**
 * Game renderer.
 *
 * @author North.
 */
public class Renderer {

  static boolean showPauseText;

  static int windowWidth = 300;
  static int tileWidth = 60;

  // Cached tiles
  static BufferedImage free;
  static BufferedImage free_2;
  static BufferedImage free_3;
  static BufferedImage free_4;
  static BufferedImage wall;
  static BufferedImage wall_2;
  static BufferedImage wall_3;
  static BufferedImage wall_4;
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

  static AudioInputStream background;

  static Font bobFont;

  // load assets
  static {
    try {
      free = ImageIO.read(new File("resources//images//free.png"));
      free_2 = ImageIO.read(new File("resources//images//free_2.png"));
      free_3 = ImageIO.read(new File("resources//images//free_3.png"));
      free_4 = ImageIO.read(new File("resources//images//free_4.png"));
      wall = ImageIO.read(new File("resources//images//wall.png"));
      wall_2 = ImageIO.read(new File("resources//images//wall_2.png"));
      wall_3 = ImageIO.read(new File("resources//images//wall_3.png"));
      wall_4 = ImageIO.read(new File("resources//images//wall_4.png"));
      lock = ImageIO.read(new File("resources//images//lock.png"));
      info = ImageIO.read(new File("resources//images//info.png"));
      treasure = ImageIO.read(new File("resources//images//treasure.png"));
      exit = ImageIO.read(new File("resources//images//exit.png"));

      key_red = ImageIO.read(new File("resources//images//key_red.png"));
      key_blue = ImageIO.read(new File("resources//images//key_blue.png"));
      key_green = ImageIO.read(new File("resources//images//key_green.png"));
      key_yellow = ImageIO.read(new File("resources//images//key_yellow.png"));
      door_red = ImageIO.read(new File("resources//images//door_red.png"));
      door_blue = ImageIO.read(new File("resources//images//door_blue.png"));
      door_green = ImageIO.read(new File("resources//images//door_green.png"));
      door_yellow = ImageIO.read(new File("resources//images//door_yellow.png"));

      chapUp = ImageIO.read(new File("resources//images//chap_up.png"));
      chapDown = ImageIO.read(new File("resources//images//chap_down.png"));
      chapLeft = ImageIO.read(new File("resources//images//chap_left.png"));
      chapRight = ImageIO.read(new File("resources//images//chap_right.png"));

      // sound
      background = AudioSystem.getAudioInputStream(new File("resources//sounds/background.wav"));
      var clip = AudioSystem.getClip();
      clip.open(background);
      clip.start();

      // fonts
      bobFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources//fonts//BobFont.otf"))
          .deriveFont(24.0F);
      GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(bobFont);

      // Not in scope to deal with these
    } catch (IOException | UnsupportedAudioFileException
        | LineUnavailableException | FontFormatException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the image for a given tile.
   */
  private static BufferedImage image(Tile tile, int x, int y) {
    // can't switch on instanceof
    if (tile instanceof Wall) {
      return getWall(x, y);
    } else if (tile instanceof Key key) {
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
    } else if (tile instanceof Door door) {
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
    } else if (tile instanceof Lock) {
      return lock;
    } else if (tile instanceof Info) {
      return info;
    } else if (tile instanceof Treasure) {
      return treasure;
    } else if (tile instanceof Exit) {
      return exit;
    } else if (tile != null) {
      return tile.getCustomImage();
    }

    return getFree(x, y);
  }

  /**
   * Returns a psuedo random variation of the wall texture, based on the coordinates.
   */
  private static BufferedImage getWall(int x, int y) {
    // helps to break up the monotony of the texture
    // uses modulo so it doesn't flicker
    switch ((x + 2 * y) % 4) {
      case 1:
      default:
        return wall;
      case 2:
        return wall_2;
      case 3:
        return wall_3;
      case 0:
        return wall_4;
    }
  }

  /**
   * Returns a psuedo random variation of the free texture, based on the coordinates.
   */
  private static BufferedImage getFree(int x, int y) {
    // helps to break up the monotony of the texture
    // uses modulo so it doesn't flicker
    switch ((x + 2 * y) % 4) {
      case 1:
      default:
        return free;
      case 2:
        return free_2;
      case 3:
        return free_3;
      case 0:
        return free_4;
    }
  }

  /**
   * Gets the  image for chap.
   */
  private static BufferedImage chap(Maze maze) {
    switch (maze.getChapDirection()) {
      case Up:
        return chapUp;
      case Down:
      default:
        return chapDown;
      case Left:
        return chapLeft;
      case Right:
        return chapRight;
    }
  }

  /**
   * Setter for show pause text.
   *
   * @param newValue new value.
   */
  public static void setShowPauseText(boolean newValue) {
    showPauseText = newValue;
  }

  /**
   * draws the given text at the given location in the bob the builder style.
   */
  public static void drawText(Graphics2D image, int x, int y, String text, Color background) {
    // Draw a rectangle
    image.setColor(background);
    image.fillRect((x - 2) * tileWidth,
        y * tileWidth, 7 * tileWidth, 3 * tileWidth);

    // Draw the text
    image.setFont(bobFont);
    image.setColor(new Color(7, 0, 45));

    int textY = y * tileWidth + 25;
    String[] lines = text.split("\n");   // Drawstring doesn't handle newlines on its own

    for (String line : lines) {
      image.drawString(line.strip(), (x - 2) * tileWidth + 5, textY);
      textY += 25;
    }
  }

  /**
   * Renders the maze.
   *
   * @param maze  maze to render.
   * @param image graphics drawing context.
   */
  public static void render(Maze maze, Graphics2D image) {
    // Recenter the map around the player
    Position position = maze.getChapPosition();
    int offsetX = (int) (position.x() * tileWidth * -1 + (windowWidth * 0.5));
    int offsetY = (int) (position.y() * tileWidth * -1 + (windowWidth * 0.5) + 60);
    image.translate(offsetX, offsetY);

    for (int x = -20; x < maze.getCols() + 20; x++) {
      for (int y = -20; y < maze.getRows() + 20; y++) {
        // Overscan somewhat to draw the walls outside the level, so it's not just empty space
        if (x < 0 || x >= maze.getCols() || y < 0 || y >= maze.getRows()) {
          image.drawImage(getFree(x, y), null, x * tileWidth, y * tileWidth);
        } else {
          image.drawImage(getFree(x, y), null, x * tileWidth, y * tileWidth);
          image.drawImage(image(maze.getTiles()[y][x], x, y), null, x * tileWidth, y * tileWidth);
        }
      }
    }

    if (showPauseText) {
      // Display pause game text
      drawText(image, maze.getChapPosition().x(), maze.getChapPosition().y(),
          "Game Paused", new Color(255, 255, 255));
    } else if (maze.getInfoText() != null) {
      // Display info tile
      drawText(image, maze.getChapPosition().x(), maze.getChapPosition().y(),
          maze.getInfoText(), new Color(255, 202, 2));
    } else {
      // Otherwise draw chap
      image.drawImage(chap(maze), null, maze.getChapPosition().x() * tileWidth,
          maze.getChapPosition().y() * tileWidth);
    }
  }

  /** Public method to get the image for a given tile,
   * Used for the inventory.
   */
  public static BufferedImage getTileImage(Tile tile) {
    BufferedImage image;
    if (tile instanceof Chap) {
      image = chapDown;
    } else if (tile instanceof Wall) {
      image = wall;
    } else {
      image = image(tile, 0, 0);
    }
    // Need to copy it to prevent mutable object errors
    var copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    var graphics = copy.getGraphics();
    graphics.drawImage(image, 0, 0, null);
    graphics.dispose();
    return copy;
  }

}
