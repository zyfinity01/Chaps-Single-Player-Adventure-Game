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
  private int timeLeft;
  private int level;

  private Position chapPosition;
  private Direction chapDirection;

  /**
   * Construct a new Maze without an inventory.
   *
   * @param tiles maze tiles.
   * @param rows count of maze rows.
   * @param cols count of maze columns.
   * @param timeLeft amount of time left.
   * @param level level number.
   */
  public Maze(Tile[][] tiles, int rows, int cols, int timeLeft, int level) {
    this(tiles, rows, cols, List.of(), timeLeft, level);
  }

  /**
   * Construct a new Maze with an inventory.
   *
   * @param tiles maze tiles.
   * @param rows count of maze rows.
   * @param cols count of maze coluns.
   * @param inventory player inventory.
   * @param timeLeft amount of time left.
   * @param level level number.
   */
  public Maze(Tile[][] tiles, int rows, int cols, List<Tile> inventory, int timeLeft, int level) {
    verifyMazeSetup(tiles, rows, cols, inventory, timeLeft, level);

    this.tiles = tiles.clone();
    this.rows = rows;
    this.cols = cols;
    this.inventory = new ArrayList<>(inventory);
    this.timeLeft = timeLeft;
    this.level = level;

    setupChapsLocation();
  }

  /**
   * Get the number of maze rows.
   *
   * @return row count.
   */
  public int getRows() {
    return rows;
  }

  /**
   * Get the number of maze columns.
   *
   * @return column count.
   */
  public int getCols() {
    return cols;
  }

  /**
   * Get the maze tiles.
   *
   * @return maze tiles.
   */
  public Tile[][] getTiles() {
    return tiles.clone();
  }

  /**
   * Get the maze inventory.
   *
   * @return maze inventory.
   */
  public List<Tile> getInventory() {
    return Collections.unmodifiableList(inventory);
  }

  /**
   * Get the player position.
   *
   * @return chaps position.
   */
  public Position getChapPosition() {
    return chapPosition;
  }

  /**
   * Get the players direction.
   *
   * @return chaps direction.
   */
  public Direction getChapDirection() {
    return chapDirection;
  }

  /**
   * Get the time left.
   *
   * @return time left.
   */
  public int getTimeLeft() {
    return timeLeft;
  }

  /**
   * Get the level number.
   *
   * @return current level.
   */
  public int getLevel() {
    return level;
  }

  /**
   * Perform a level tick.
   */
  public void tick() {
    if (this.timeLeft <= 0) {
      return;
    }

    // tick all the actors
    for (var tile : getTilesOfType(Tile.class)) {
      tile.getValue().tick(tiles, tile.getKey());
    }

    this.timeLeft--;
  }

  /**
   * Check if the game is over.
   *
   * @return game over state.
   */
  public State getState() {
    // have we run out of time
    if (timeLeft <= 0) {
      return State.OutOfTime;
    }

    // have we reached the exit tile
    var tile = tiles[chapPosition.y()][chapPosition.x()];
    if (tile != null && tile instanceof Exit) {
      return State.Complete;
    }

    // have we hit the custom actor
    if (tile != null && tile.getClass().getName().contains("Actor")) {
      return State.Dead;
    }

    return State.Running;
  }

  /**
   * Check if the player can move in a direction.
   *
   * @param direction the target direction.
   * @return whether the player can move there.
   */
  public boolean canMoveChap(Direction direction) {
    var current = getChapPosition();
    var next = new Position(current.x() + direction.getX(), current.y() + direction.getY());

    // check if out of map bounds
    if (next.x() < 0 || next.y() < 0) {
      return false;
    }

    if (next.x() >= cols || next.y() >= rows) {
      return false;
    }

    // check if out of time
    if (timeLeft <= 0) {
      return false;
    }

    // now check the tile at that position
    var tile = tiles[next.y()][next.x()];
    return tile == null || tile.canInteractWithPlayer(tiles, inventory);
  }

  /**
   * Moves chap in the current direction.
   *
   * @param direction the target direction.
   */
  public void moveChap(Direction direction) {
    // double check we can move there
    if (!canMoveChap(direction)) {
      throw new IllegalArgumentException("Cannot move chap in that direction");
    }

    // move chap to the target
    chapDirection = direction;
    chapPosition = new Position(
      chapPosition.x() + direction.getX(),
      chapPosition.y() + direction.getY());

    // record count to later validate state
    var treasureCount = getCountOfTotalTiles(Treasure.class);

    // now iteract with the tile we moved to
    var tile = tiles[chapPosition.y()][chapPosition.x()];
    if (tile != null) {
      tile.interactWithPlayer(tiles, inventory, chapPosition);
    }

    // then reaffirm the tile count
    assert treasureCount == getCountOfTotalTiles(Treasure.class);
  }

  /**
   * Finds tiles of a certain type.
   *
   * @param <T> type to search for.
   * @param type type to search for.
   * @return A list of found tile-position entries.
   */
  public <T extends Tile> List<Entry<Position, T>> getTilesOfType(Class<T> type) {
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
   * Counts how many tiles of a given type there are in
   * both the maze.
   *
   * @param <T> the type to search for.
   * @param type the type to search for.
   * @return the count of tile type.
   */
  public <T extends Tile> int getCountOfMazeTiles(Class<T> type) {
    return getTilesOfType(type).size();
  }

  /**
   * Counts how many tiles of a given type there are in
   * both the maze and player inventory.
   *
   * @param <T> the type to search for.
   * @param type the type to search for.
   * @return the count of tile type.
   */
  private <T extends Tile> int getCountOfTotalTiles(Class<T> type) {
    var count = getCountOfMazeTiles(type);
    count += inventory.stream().filter(type::isInstance).count();
    return count;
  }

  /**
   * Returns the current info text if any.
   *
   * @return the info text or null.
   */
  public String getInfoText() {
    var tile = tiles[chapPosition.y()][chapPosition.x()];

    if (tile instanceof Info) {
      var info = (Info) tile;
      return info.text();
    }

    return null;
  }

  /**
   * Verifies the maze is valid.
   *
   * @param tiles maze tiles.
   * @param rows row count.
   * @param cols col count.
   * @param inventory player inventory.
   */
  private void verifyMazeSetup(Tile[][] tiles, int rows,
      int cols, List<Tile> inventory, int timeLeft, int level) {
    if (tiles == null) {
      throw new IllegalStateException("Tiles cannot be null");
    }

    if (inventory == null) {
      throw new IllegalStateException("Inventory cannot be null");
    }

    if (tiles.length != rows) {
      throw new IllegalStateException("Rows don't match maze");
    }

    if (rows <= 0 || cols <= 0) {
      throw new IllegalStateException("Must have positive rows and cols");
    }

    for (int i = 0; i < tiles.length; i++) {
      if (tiles[i].length != cols) {
        throw new IllegalStateException("Cols don't match maze");
      }
    }

    if (timeLeft <= 0) {
      throw new IllegalStateException("Time left must be greater than 0");
    }

    if (level <= 0) {
      throw new IllegalStateException("Level numbers start at 1");
    }
  }

  /**
   * Pulls chap out of the map tiles into their own space.
   */
  private void setupChapsLocation() {
    var found = getTilesOfType(Chap.class);
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
