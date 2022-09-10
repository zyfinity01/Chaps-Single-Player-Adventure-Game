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
    this(tiles, List.of(), rows, cols);
  }

  /**
   * Construct a new Maze with an inventory.
   * @param tiles maze tiles.
   * @param rows count of maze rows.
   * @param cols count of maze coluns
   * @param inventory player inventory.
   */
  public Maze(Tile[][] tiles, List<Tile> inventory, int rows, int cols) {
    this.tiles = tiles.clone();
    this.inventory = List.copyOf(inventory);
    this.rows = rows;
    this.cols = cols;
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
}
