package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Collections;
import java.util.List;

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

    for (int i = 0; i < tiles.length; i++) {
      if (tiles[i].length != cols) {
        throw new IllegalArgumentException("Cols don't match maze");
      }
    }
  }
}
