package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * The base-type for all Maze tile objects.
 *
 * @author Jonty Morris, 300563915
 */
public interface Tile {
  /**
   * Check if the player can interact with this.
   *
   * @param tiles the maze tiles.
   * @param inventory the player inventory.
   * @return state of interaction.
   */
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory);

  /**
   * Perform tile-specific interaction with the player.
   *
   * @param tiles the maze tiles.
   * @param inventory the player inventory.
   * @param position the player position.
   */
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position);

  /**
   * Perform tile-specific updates on game tick.
   *
   * @param tiles the maze tiles.
   * @param position the tile position.
   */
  public void tick(Tile[][] tiles, Position position);
}
