package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Contains the state for a maze level.
 * 
 * @author Jonty Morris, 300563915.
 */
public class Maze {
  private int rows;
  private int cols;
  private Tile[][] tiles;
  private List<Tile> inventory;

  private Position chapPosition;
  private Direction chapDirection;

  /**
   * Construct a new Maze without an inventory.
   * @param tiles maze tiles.
   * @param rows count of maze rows.
   * @param cols count of maze coluns.
   */
  public Maze(Tile[][] tiles, int rows, int cols) {
    this(tiles, rows, cols, List.of());
  }

  /**
   * Construct a new Maze with an inventory.
   * @param tiles maze tiles.
   * @param rows count of maze rows.
   * @param cols count of maze coluns
   * @param inventory player inventory
   */
  public Maze(Tile[][] tiles, int rows, int cols, List<Tile> inventory) {
    verifyMazeSetup(tiles, rows, cols, inventory);

    this.tiles = tiles.clone();
    this.rows = rows;
    this.cols = cols;
    this.inventory = List.copyOf(inventory);

    setupChapsLocation();
  }

  /**
   * Get the number of maze rows.
   * @return row count.
   */
  public int getRows() {
    return rows;
  }

  /**
   * Get the number of maze columns.
   * @return column count.
   */
  public int getCols() {
    return cols;
  }

  /**
   * Get the maze tiles.
   * @return maze tiles.
   */
  public Tile[][] getTiles() {
    return tiles.clone();
  }

  /**
   * Get the maze inventory.
   * @return maze inventory.
   */
  public List<Tile> getInventory() {
    return Collections.unmodifiableList(inventory);
  }

  /**
   * Get the player position.
   * @return chaps position.
   */
  public Position getChapPosition() {
    return chapPosition;
  }

  /**
   * Get the players direction.
   * @return chaps direction.
   */
  public Direction getChapDirection() {
    return chapDirection;
  }

  /**
   * Finds tiles of a certain type.
   * @param <T> type to search for.
   * @param type type to search for.
   * @return A list of found tile-position entries.
   */
  private <T extends Tile> List<Entry<Position, T>> findTilesOfType(Class<T> type) {
    var found = new ArrayList<Entry<Position, T>>();

    // go through the tiles
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        // check if its the target type
        if (type.isInstance(tiles[y][x])) {
          // append the tile and its position
          var entry = Map.entry(new Position(x, y), type.cast(tiles[y][x]));
          found.add(entry);
        }
      }
    }

    return found;
  }

  /**
   * Verifies the maze is valid.
   * @param tiles maze tiles.
   * @param rows row count.
   * @param cols col count.
   * @param inventory player inventory.
   */
  private void verifyMazeSetup(Tile[][] tiles, int rows, int cols, List<Tile> inventory) {
    if (tiles == null) {
      throw new IllegalArgumentException("Tiles cannot be null");
    }

    if (inventory == null) {
      throw new IllegalArgumentException("Inventory cannot be null");
    }

    if (tiles.length != rows) {
      throw new IllegalArgumentException("Rows don't match maze");
    }

    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Must have positive rows and cols.");
    }

    for (int i = 0; i < tiles.length; i++) {
      if (tiles[i].length != cols) {
        throw new IllegalArgumentException("Cols don't match maze");
      }
    }
  }

  /**
   * Pulls chap out of the map tiles into their own space.
   */
  private void setupChapsLocation() {
    var found = findTilesOfType(Chap.class);
    if (found.size() != 1) {
      throw new IllegalStateException("Must specify a single chap");
    }

    // record chaps position
    var chap = found.get(0);
    chapPosition = chap.getKey();
    chapDirection = Direction.Right;

    // remove chap from the map tiles
    tiles[chapPosition.y()][chapPosition.x()] = null;
  }
}
